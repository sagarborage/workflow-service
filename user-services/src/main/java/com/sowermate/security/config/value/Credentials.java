package com.sowermate.security.config.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    private String username;
    private String password;
    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }
}