package com.yakuen.ceisa.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yakuen.ceisa.dto.RegisterRequest;
import com.yakuen.ceisa.dto.UpdateUserRequest;
import com.yakuen.ceisa.dto.UserResponse;
import com.yakuen.ceisa.exception.BusinessException;
import com.yakuen.ceisa.mapper.UserMapper;
import com.yakuen.ceisa.model.Role;
import com.yakuen.ceisa.model.User;
import com.yakuen.ceisa.service.RoleService;
import com.yakuen.ceisa.service.UserManagementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

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

        return toResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        return toResponse(requireUser(id));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userMapper.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User existingUser = requireUser(id);

        User duplicateByEmail = userMapper.findByEmail(request.getEmail().trim().toLowerCase());
        if (duplicateByEmail != null && !duplicateByEmail.getId().equals(id)) {
            throw new BusinessException(HttpStatus.CONFLICT, "Email sudah digunakan user lain");
        }

        existingUser.setEmail(request.getEmail().trim().toLowerCase());
        existingUser.setFullName(request.getFullName().trim());
        existingUser.setActive(request.getActive());
        existingUser.setUpdatedAt(LocalDateTime.now());
        userMapper.update(existingUser);
        return toResponse(existingUser);
    }

    private void validateUniqueUser(String username, String email) {
        if (userMapper.findByUsername(username.trim()) != null) {
            throw new BusinessException(HttpStatus.CONFLICT, "Username sudah digunakan");
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
        List<String> roleCodes = roleService.getUserRoles(user.getId()).stream()
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
