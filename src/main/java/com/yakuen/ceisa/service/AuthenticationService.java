package com.yakuen.ceisa.service;

import com.yakuen.ceisa.dto.LoginRequest;
import com.yakuen.ceisa.dto.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

    void logout(String token);
}
