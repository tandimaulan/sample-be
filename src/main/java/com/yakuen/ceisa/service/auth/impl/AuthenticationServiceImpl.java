package com.yakuen.ceisa.service.auth.impl;

import com.yakuen.ceisa.dto.auth.LoginRequest;
import com.yakuen.ceisa.dto.auth.LoginResponse;
import com.yakuen.ceisa.exception.BusinessException;
import com.yakuen.ceisa.mapper.auth.UserMapper;
import com.yakuen.ceisa.model.auth.Role;
import com.yakuen.ceisa.model.auth.User;
import com.yakuen.ceisa.service.auth.AuthenticationService;
import com.yakuen.ceisa.service.auth.RoleService;
import com.yakuen.ceisa.utils.SessionIdUtil;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserMapper userMapper;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;
  private final SessionIdUtil sessionIdUtil;
  private final ConcurrentMap<String, Long> activeSessions =
    new ConcurrentHashMap<>();

  @Override
  public LoginResponse login(LoginRequest request) {
    String usernameOrEmail = request.getUsernameOrEmail().trim();
    User user = userMapper.findByUsernameOrEmail(usernameOrEmail);
    if (
      user == null ||
      !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())
    ) {
      throw new BusinessException(
        HttpStatus.UNAUTHORIZED,
        "Username/email atau password tidak valid"
      );
    }
    if (!Boolean.TRUE.equals(user.getActive())) {
      throw new BusinessException(HttpStatus.FORBIDDEN, "User tidak aktif");
    }

    String authToken = UUID.randomUUID().toString();
    activeSessions.put(authToken, user.getId());

    List<String> roles = roleService
      .getUserRoles(user.getId())
      .stream()
      .map(Role::getRoleCode)
      .toList();

    return LoginResponse.builder()
      .userId(user.getId())
      .username(user.getUsername())
      .fullName(user.getFullName())
      .email(user.getEmail())
      .authToken(authToken)
      .roles(roles)
      .build();
  }

  @Override
  public LoginResponse loginWithSessionId(String sessionId) {
    LoginRequest loginRequest = sessionIdUtil.decodeToLoginRequest(sessionId);
    return login(loginRequest);
  }

  @Override
  public void logout(String token) {
    if (activeSessions.remove(token) == null) {
      throw new BusinessException(
        HttpStatus.NOT_FOUND,
        "Session token tidak ditemukan"
      );
    }
  }

  @Override
  public Long getUserIdByToken(String token) {
    return activeSessions.get(token);
  }
}
