package com.sowermate.tenantService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    // Inject the cors.allowedOrigins property into the origins list
/*    @Value("#{'${cors.allowedOrigins}'.split(',')}")
    private List<String> allowedOrigins;

    @Value("#{'${cors.allowedMethods}'.split(',')}")
    private List<String> allowedMethods;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
        // ... other security configurations
        ;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        System.out.println("allowedOrigins: " + allowedOrigins.toString());
        System.out.println("allowedMethods: " + allowedMethods.toString());
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/
}
