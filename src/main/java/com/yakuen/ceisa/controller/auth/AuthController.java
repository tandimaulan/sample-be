package com.yakuen.ceisa.controller.auth;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yakuen.ceisa.dto.auth.ChangePasswordRequest;
import com.yakuen.ceisa.dto.auth.ForgotPasswordRequest;
import com.yakuen.ceisa.dto.auth.LoginRequest;
import com.yakuen.ceisa.dto.auth.LoginResponse;
import com.yakuen.ceisa.dto.auth.ResetPasswordRequest;
import com.yakuen.ceisa.service.auth.AuthenticationService;
import com.yakuen.ceisa.service.auth.PasswordManagementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final PasswordManagementService passwordManagementService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/login-with-session")
    public LoginResponse loginWithSession(@RequestParam String sessionId) {
        return authenticationService.loginWithSessionId(sessionId);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestParam String token) {
        authenticationService.logout(token);
    }

    @PostMapping("/forgot-password")
    public Map<String, String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        String resetToken = passwordManagementService.createResetToken(request);
        return Map.of(
                "message", "Reset password token generated",
                "resetToken", resetToken
        );
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordManagementService.resetPassword(request);
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        passwordManagementService.changePassword(request);
    }
}
