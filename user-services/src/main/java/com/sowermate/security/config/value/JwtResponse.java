package com.sowermate.security.config.value;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String userUuid;
    private String userPhoneNumber;
    private String companyUuid;
    private String companyName;
    private String companyAddress;
    private List<String> roles;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiry;
    private Date refreshTokenExpiry;
}