package com.sowermate.tenantService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    // Inject the cors.allowedOrigins property into the origins list
    @Value("#{'${cors.allowedOrigins}'.split(',')}")
    private List<String> allowedOrigins;

    @Value("#{'${cors.allowedMethods}'.split(',')}")
    private List<String> allowedMethods;

    @Value("#{'${cors.allowedHeaders}'.split(',')}")
    private List<String> allowedHeaders;

    @Value("#{${cors.allowCredentials}}")
    private boolean allowCredentials;
    @Value("#{${cors.maxAge}}")
    private Long maxAge;

    @Order(1)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        System.out.println("allowedOrigins: " + allowedOrigins.toString());
        System.out.println("allowedMethods: " + allowedMethods.toString());
        System.out.println("allowedHeaders: " + allowedHeaders.toString());
        System.out.println("allowCredentials: " + allowCredentials);
        System.out.println("maxAge: " + maxAge);
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setAllowCredentials(allowCredentials);
        configuration.setMaxAge(maxAge);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
