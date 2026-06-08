package com.yakuen.ceisa.service.impl;

import com.yakuen.ceisa.dto.ChangePasswordRequest;
import com.yakuen.ceisa.dto.ForgotPasswordRequest;
import com.yakuen.ceisa.dto.ResetPasswordRequest;
import com.yakuen.ceisa.exception.BusinessException;
import com.yakuen.ceisa.mapper.PasswordResetTokenMapper;
import com.yakuen.ceisa.mapper.UserMapper;
import com.yakuen.ceisa.model.PasswordResetToken;
import com.yakuen.ceisa.model.User;
import com.yakuen.ceisa.service.PasswordManagementService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordManagementServiceImpl implements PasswordManagementService {

    private final UserMapper userMapper;
    private final PasswordResetTokenMapper passwordResetTokenMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = requireUser(request.getUserId());
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Password lama tidak sesuai");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updatePassword(user);
    }

    @Override
    public String createResetToken(ForgotPasswordRequest request) {
        User user = userMapper.findByEmail(request.getEmail());
        if (user == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Email tidak terdaftar");
        }

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUserId(user.getId());
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setCreatedAt(LocalDateTime.now());
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(30));
        resetToken.setUsed(0);
        passwordResetTokenMapper.insert(resetToken);
        return resetToken.getToken();
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = passwordResetTokenMapper.findByToken(request.getToken());
        if (resetToken == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Token reset password tidak ditemukan");
        }
        if (resetToken.getUsed() != null && resetToken.getUsed() == 1) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Token reset password sudah digunakan");
        }
        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Token reset password sudah kedaluwarsa");
        }

        User user = requireUser(resetToken.getUserId());
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updatePassword(user);
        passwordResetTokenMapper.markAsUsed(resetToken.getId());
    }

    private User requireUser(Long userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "User tidak ditemukan");
        }
        return user;
    }
}
