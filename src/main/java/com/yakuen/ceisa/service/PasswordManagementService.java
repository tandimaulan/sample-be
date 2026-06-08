package com.yakuen.ceisa.service;

import com.yakuen.ceisa.dto.ChangePasswordRequest;
import com.yakuen.ceisa.dto.ForgotPasswordRequest;
import com.yakuen.ceisa.dto.ResetPasswordRequest;

public interface PasswordManagementService {

    void changePassword(ChangePasswordRequest request);

    String createResetToken(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
