package com.sowermate.user.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private String userProfileUuid;
    private String tenantUuid;

}
