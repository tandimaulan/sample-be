package com.yakuen.ceisa.service.auth;

import java.util.List;

import com.yakuen.ceisa.dto.auth.RegisterRequest;
import com.yakuen.ceisa.dto.auth.UpdateUserRequest;
import com.yakuen.ceisa.dto.auth.UserResponse;

public interface UserManagementService {

    UserResponse createUser(RegisterRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers(String authorizationHeader, String xAuthToken);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    UserResponse getUserByUsernameOrEmail(String usernameOrEmail);
}
