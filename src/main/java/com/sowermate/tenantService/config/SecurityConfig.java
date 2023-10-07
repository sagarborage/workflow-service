package com.sowermate.tenantService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.protected-urls}")
    private String[] protectedUrls;

    @Value("${security.unprotected-urls}")
    private String[] unprotectedUrls;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configure unprotected URLs
        for (String urlPattern : unprotectedUrls) {
            http.authorizeRequests().antMatchers(urlPattern).permitAll();
        }

        // Configure protected URLs
        for (String urlPattern : protectedUrls) {
            http.authorizeRequests().antMatchers(urlPattern).authenticated();
        }

        // Apply basic authentication
        http.httpBasic();

        // Ensure sessions are stateless (optional, but recommended for APIs)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Apply CORS Config
        http.cors();
        // Other configurations
        http.csrf().disable();  // This is for demonstration; consider CSRF protection for non-API, form-based authentication.
    }
}
