package com.yakuen.ceisa.service.auth;

import com.yakuen.ceisa.dto.auth.RegisterRequest;
import com.yakuen.ceisa.dto.auth.UpdateUserRequest;
import com.yakuen.ceisa.dto.auth.UserResponse;
import java.util.List;

public interface UserManagementService {
  UserResponse createUser(RegisterRequest request);

  UserResponse getUserById(Long id);

  List<UserResponse> getAllUsers(String authorizationHeader, String xAuthToken);

  UserResponse updateUser(Long id, UpdateUserRequest request);

  UserResponse getUserByUsernameOrEmail(String usernameOrEmail);
}
