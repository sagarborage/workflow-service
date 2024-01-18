package com.sowermate.user.services;

import com.sowermate.user.dtos.ChangePasswordDto;
import com.sowermate.user.dtos.ForgotPasswordRequestDto;
import com.sowermate.user.dtos.LoginDto;
import com.sowermate.user.dtos.VerifyAndSetPasswordDto;

public interface UserService {
    Boolean emailVerification(String token);
    String verifyAccountAndSetPassword(VerifyAndSetPasswordDto verifyAndSetPasswordDto);
    Boolean regenerateCode(String email);
    String changePassword(ChangePasswordDto changePasswordDto);
    Boolean forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);
    Boolean setPassword(LoginDto loginDto);
    void sendOtp(String email,Long userId);
    boolean validateOtpAndUpdateOtpStatus(String newEmail,String enteredOtp);
}
