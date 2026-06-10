package com.yakuen.ceisa.service.auth;

import com.yakuen.ceisa.dto.auth.ChangePasswordRequest;
import com.yakuen.ceisa.dto.auth.ForgotPasswordRequest;
import com.yakuen.ceisa.dto.auth.ResetPasswordRequest;

public interface PasswordManagementService {

    void changePassword(ChangePasswordRequest request);

    String createResetToken(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
