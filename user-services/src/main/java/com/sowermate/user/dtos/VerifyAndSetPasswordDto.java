package com.sowermate.user.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyAndSetPasswordDto {
    private String password;
    private String confirmationCode;
}
