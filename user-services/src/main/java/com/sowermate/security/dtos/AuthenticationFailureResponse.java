package com.sowermate.security.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuthenticationFailureResponse {

    private Date timestamp;
    private String error;
    private String message;
    private boolean isTokenExpired;

    public AuthenticationFailureResponse() {
        this.timestamp = new Date();
        this.error = "Unauthorized";
    }
}
