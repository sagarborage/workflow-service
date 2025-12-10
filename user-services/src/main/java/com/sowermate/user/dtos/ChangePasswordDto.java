package com.sowermate.user.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {
    private String username;
    private String oldPassword;
    private String newPassword;
}
