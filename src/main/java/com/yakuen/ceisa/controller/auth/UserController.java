package com.yakuen.ceisa.controller.auth;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yakuen.ceisa.dto.auth.RegisterRequest;
import com.yakuen.ceisa.dto.auth.UpdateUserRequest;
import com.yakuen.ceisa.dto.auth.UserResponse;
import com.yakuen.ceisa.service.auth.UserManagementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserManagementService userManagementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody RegisterRequest request) {
        return userManagementService.createUser(request);
    }

    @GetMapping
    public List<UserResponse> getUsers(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestHeader(value = "X-Auth-Token", required = false) String xAuthToken) {
        return userManagementService.getAllUsers(authorizationHeader, xAuthToken);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userManagementService.getUserById(id);
    }

    @PostMapping("/{id}/update")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return userManagementService.updateUser(id, request);
    }

    @GetMapping("/username-or-email/{usernameOrEmail}")
    public UserResponse getUserByUsernameOrEmail(@PathVariable String usernameOrEmail) {
        return userManagementService.getUserByUsernameOrEmail(usernameOrEmail);
    }
}
