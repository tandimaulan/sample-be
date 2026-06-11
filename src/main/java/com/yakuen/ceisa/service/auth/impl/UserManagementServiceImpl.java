package com.yakuen.ceisa.service.auth.impl;

import com.yakuen.ceisa.dto.auth.RegisterRequest;
import com.yakuen.ceisa.dto.auth.UpdateUserRequest;
import com.yakuen.ceisa.dto.auth.UserResponse;
import com.yakuen.ceisa.exception.BusinessException;
import com.yakuen.ceisa.mapper.auth.RoleMapper;
import com.yakuen.ceisa.mapper.auth.UserMapper;
import com.yakuen.ceisa.model.auth.Role;
import com.yakuen.ceisa.model.auth.User;
import com.yakuen.ceisa.service.auth.AuthenticationService;
import com.yakuen.ceisa.service.auth.RoleService;
import com.yakuen.ceisa.service.auth.UserManagementService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

  private final UserMapper userMapper;
  private final RoleMapper roleMapper;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationService authenticationService;

  @Override
  @Transactional
  public UserResponse createUser(RegisterRequest request) {
    validateUniqueUser(request.getUsername(), request.getEmail());

    LocalDateTime now = LocalDateTime.now();
    User user = new User();
    user.setUsername(request.getUsername().trim());
    user.setEmail(request.getEmail().trim().toLowerCase());
    user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    user.setFullName(request.getFullName().trim());
    user.setActive(Boolean.TRUE);
    user.setCreatedAt(now);
    user.setUpdatedAt(now);
    userMapper.insert(user);
    roleService.assignDefaultRole(user.getId());
    return toResponse(user);
  }

  @Override
  public UserResponse getUserById(Long id) {
    return toResponse(requireUser(id));
  }

  @Override
  public List<UserResponse> getAllUsers(
    String authorizationHeader,
    String xAuthToken
  ) {
    Long currentUserId = getCurrentUserId(authorizationHeader, xAuthToken);

    return userMapper
      .findAll()
      .stream()
      .filter(user -> !hasRoleMaster(user.getId()))
      // .filter(user -> !user.getId().equals(currentUserId))
      .map(this::toResponse)
      .toList();
  }

  @Override
  @Transactional
  public UserResponse updateUser(Long id, UpdateUserRequest request) {
    User existingUser = requireUser(id);

    String normalizedEmail = request.getEmail().trim().toLowerCase();
    User duplicateByEmail = userMapper.findByEmail(normalizedEmail);
    if (duplicateByEmail != null && !duplicateByEmail.getId().equals(id)) {
      throw new BusinessException(
        HttpStatus.CONFLICT,
        "Email sudah digunakan user lain"
      );
    }

    existingUser.setEmail(normalizedEmail);
    existingUser.setFullName(request.getFullName().trim());
    existingUser.setUpdatedAt(LocalDateTime.now());

    if (request.getActive() != null) {
      existingUser.setActive(request.getActive());
    }

    userMapper.update(existingUser);

    if (request.getRoleCodes() == null || request.getRoleCodes().isEmpty()) {
      // roleService.assignDefaultRole(existingUser.getId());
    } else {
      validateRoleCodes(request.getRoleCodes());
      roleService.assignRolesToUser(
        existingUser.getId(),
        request.getRoleCodes()
      );
    }

    return toResponse(existingUser);
  }

  @Override
  public UserResponse getUserByUsernameOrEmail(String usernameOrEmail) {
    User user = userMapper.findByUsernameOrEmail(usernameOrEmail.trim());
    if (user == null) {
      throw new BusinessException(HttpStatus.NOT_FOUND, "User tidak ditemukan");
    }
    return toResponse(user);
  }

  private Long getCurrentUserId(String authorizationHeader, String xAuthToken) {
    String authToken = extractAuthToken(authorizationHeader, xAuthToken);
    Long userId = authenticationService.getUserIdByToken(authToken);
    if (userId != null) {
      return userId;
    }
    throw new BusinessException(
      HttpStatus.UNAUTHORIZED,
      "User not authenticated"
    );
  }

  private String extractAuthToken(
    String authorizationHeader,
    String xAuthToken
  ) {
    if (authorizationHeader != null) {
      String trimmedAuthorization = authorizationHeader.trim();
      if (trimmedAuthorization.regionMatches(true, 0, "Bearer ", 0, 7)) {
        return trimmedAuthorization.substring(7).trim();
      }
      if (!trimmedAuthorization.isEmpty()) {
        return trimmedAuthorization;
      }
    }

    if (xAuthToken != null && !xAuthToken.trim().isEmpty()) {
      return xAuthToken.trim();
    }

    throw new BusinessException(
      HttpStatus.UNAUTHORIZED,
      "User not authenticated"
    );
  }

  private boolean hasRoleMaster(Long userId) {
    List<Role> roles = roleService.getUserRoles(userId);
    return roles
      .stream()
      .anyMatch(role -> "MASTER".equalsIgnoreCase(role.getRoleCode()));
  }

  private void validateRoleCodes(List<String> roleCodes) {
    for (String roleCode : roleCodes) {
      Role role = roleMapper.findByCode(roleCode);
      if (role == null) {
        throw new BusinessException(
          HttpStatus.BAD_REQUEST,
          "Role not found: " + roleCode
        );
      }
    }
  }

  private void validateUniqueUser(String username, String email) {
    if (userMapper.findByUsername(username.trim()) != null) {
      throw new BusinessException(
        HttpStatus.CONFLICT,
        "Username sudah digunakan"
      );
    }
    if (userMapper.findByEmail(email.trim().toLowerCase()) != null) {
      throw new BusinessException(HttpStatus.CONFLICT, "Email sudah digunakan");
    }
  }

  private User requireUser(Long userId) {
    User user = userMapper.findById(userId);
    if (user == null) {
      throw new BusinessException(HttpStatus.NOT_FOUND, "User tidak ditemukan");
    }
    return user;
  }

  private UserResponse toResponse(User user) {
    List<String> roleCodes = roleService
      .getUserRoles(user.getId())
      .stream()
      .map(Role::getRoleCode)
      .toList();
    return UserResponse.builder()
      .id(user.getId())
      .username(user.getUsername())
      .email(user.getEmail())
      .fullName(user.getFullName())
      .active(user.getActive())
      .createdAt(user.getCreatedAt())
      .updatedAt(user.getUpdatedAt())
      .roles(roleCodes)
      .build();
  }
}
