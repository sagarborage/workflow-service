package com.sowermate.user.controllers;

import com.sowermate.user.dtos.ChangePasswordDto;
import com.sowermate.user.dtos.ForgotPasswordRequestDto;
import com.sowermate.user.dtos.LoginDto;
import com.sowermate.user.dtos.VerifyAndSetPasswordDto;
import com.sowermate.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sowermate.user.constants.MessageConstants.*;

@RestController("userControllers")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getUser() {
        return "Suhas Borage";
    }

    @PutMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam("newEmail") String newEmail, @RequestParam("otp") String enteredOtp) {
        boolean isOtpVerified = userService.validateOtpAndUpdateOtpStatus(newEmail,enteredOtp);
        if (isOtpVerified) {
            return ResponseEntity.ok(OTP_VERIFIED_MESSAGE);
        } else {
            return ResponseEntity.badRequest().body(INVALID_OTP_MESSAGE);
        }
    }

    @PutMapping("/verify-email")
    public ResponseEntity<String> emailVerification(@RequestParam String token) {
        Boolean flag = this.userService.emailVerification(token);
        if (flag)
            return new ResponseEntity<>(EMAIL_VERIFIED_MESSAGE, HttpStatus.OK);
        else
            return new ResponseEntity<>(INVALID_TOKEN_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestBody VerifyAndSetPasswordDto verifyAndSetPasswordDto) {
        String data = this.userService.verifyAccountAndSetPassword(verifyAndSetPasswordDto);
        return switch (data) {
            case PASSWORD_SET_MESSAGE -> new ResponseEntity<>(data, HttpStatus.OK);
            case INVALID_CONFIRMATION_CODE_MESSAGE ->
                    new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(UNEXPECTED_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @PutMapping("/regenerate-code")
    public ResponseEntity<String> regenerateCode(@RequestParam String email) {
        Boolean flag = this.userService.regenerateCode(email);
        if (flag)
            return new ResponseEntity<>(CONFIRMATION_CODE_SENT_SUCCESSFULLY_MESSAGE, HttpStatus.OK);
        else
            return new ResponseEntity<>(UNABLE_TO_SEND_CONFIRMATION_CODE_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String result = this.userService.changePassword(changePasswordDto);
        return switch (result) {
            case PASSWORD_CHANGED_MESSAGE -> new ResponseEntity<>(result, HttpStatus.OK);
            case CURRENT_PASSWORD_INVALID_MESSAGE, OLD_PASSWORD_AND_NEW_PASSWORD_SAME_MESSAGE ->
                    new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            case USER_NOT_EXIT_MESSAGE -> new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            default -> new ResponseEntity<>(UNEXPECTED_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) {
        if (forgotPasswordRequestDto.getUserCaptcha().equals(forgotPasswordRequestDto.getExpectedCaptcha())) {
            Boolean flag = userService.forgotPassword(forgotPasswordRequestDto);
            if (flag)
                return ResponseEntity.ok(String.format(FORGOT_PASSWORD_MESSAGE, forgotPasswordRequestDto.getEmail()));
            else
                return ResponseEntity.internalServerError().body(EMAIL_SENDING_ERROR);
        } else {
            return ResponseEntity.badRequest().body(INVALID_CAPTCHA_MESSAGE);
        }
    }

    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestBody LoginDto loginDto) {
        Boolean flag = this.userService.setPassword(loginDto);
        if (flag)
            return new ResponseEntity<>(RESET_PASSWORD_SUCCESSFULLY_MESSAGE, HttpStatus.OK);
        else
            return new ResponseEntity<>(INVALID_USERNAME_MESSAGE, HttpStatus.BAD_REQUEST);
    }


}