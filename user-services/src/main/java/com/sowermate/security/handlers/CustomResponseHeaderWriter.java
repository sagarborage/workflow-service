package com.sowermate.security.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

public class CustomResponseHeaderWriter {

    private final String allowedOrigins;

    private final String allowedMethods;

    private final String allowedHeaders;

    private final boolean allowCredentials;

    private final Long maxAge;

    public CustomResponseHeaderWriter(String[] allowedOrigins, String[] allowedMethods, String[] allowedHeaders, boolean allowCredentials, Long maxAge) {
        this.allowedOrigins = String.join(", ", allowedOrigins);
        this.allowedMethods = String.join(", ", allowedMethods);
        this.allowedHeaders = String.join(", ", allowedHeaders);
        this.allowCredentials = allowCredentials;
        this.maxAge = maxAge;
    }

    public void writeCommonHeaders(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Set CORS headers
        String origin = request.getHeader("Origin");
        if (StringUtils.isNotBlank(origin) && this.allowedOrigins.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", String.valueOf(this.allowCredentials));
        }
        response.setHeader("Access-Control-Allow-Methods", this.allowedMethods);
        response.setHeader("Access-Control-Allow-Headers", this.allowedHeaders);
        response.setHeader("Access-Control-Max-Age", String.valueOf(this.maxAge));
    }
}
