package com.sowermate.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${authentication.jwt.token.secret}")
    private String secret;

    @Value("${authentication.jwt.accessToken.expiry}")
    private long accessTokenExpiry;

    @Value("${authentication.jwt.refreshToken.expiry}")
    private long refreshTokenExpiry;

    public String getSecret() {
        return secret;
    }

    public long getAccessTokenExpiry() {
        return accessTokenExpiry;
    }
    public long getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }

}
