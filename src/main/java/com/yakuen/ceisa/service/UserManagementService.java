package com.yakuen.ceisa.service;

import com.yakuen.ceisa.dto.RegisterRequest;
import com.yakuen.ceisa.dto.UpdateUserRequest;
import com.yakuen.ceisa.dto.UserResponse;
import java.util.List;

public interface UserManagementService {

    UserResponse createUser(RegisterRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UpdateUserRequest request);
}
