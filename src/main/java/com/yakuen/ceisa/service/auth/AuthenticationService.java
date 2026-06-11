package com.yakuen.ceisa.service.auth;

import com.yakuen.ceisa.dto.auth.LoginRequest;
import com.yakuen.ceisa.dto.auth.LoginResponse;

public interface AuthenticationService {
  LoginResponse login(LoginRequest request);
  LoginResponse loginWithSessionId(String sessionId);
  void logout(String token);

  Long getUserIdByToken(String token);
}
