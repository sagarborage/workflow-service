package com.sowermate.base.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticatedUserDetails {
    private String fullName;
    private String username;
    private boolean isAuthenticated;
}
