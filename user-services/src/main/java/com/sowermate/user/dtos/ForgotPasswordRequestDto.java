package com.sowermate.user.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequestDto {
    private String email;
    private String userCaptcha;
    private String expectedCaptcha;
}
