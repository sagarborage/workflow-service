package com.sowermate.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UrlConfig {
    @Value("${security.protected-urls}")
    private String[] protectedUrls;

    @Value("${security.unprotected-urls}")
    private String[] unprotectedUrls;
}
