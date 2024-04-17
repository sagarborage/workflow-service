package com.sowermate.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sowermate.security.config.AuthConstants;
import com.sowermate.security.config.value.Credentials;
import com.sowermate.security.handlers.AuthenticationFailureHandler;
import com.sowermate.security.handlers.TokenAuthenticationSuccessHandler;
import com.sowermate.security.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomUserDetailsService userDetailsService;
    private final TokenAuthenticationSuccessHandler authenticationSuccessHandler;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final ObjectMapper objectMapper;


    public JwtAuthenticationFilter(CustomUserDetailsService userDetailsService, TokenAuthenticationSuccessHandler authenticationSuccessHandler, PasswordEncoder passwordEncoder, ObjectMapper objectMapper, AuthenticationFailureHandler authenticationFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.getContextHolderStrategy().clearContext();
        this.logger.trace("Failed to process authentication request", failed);
        this.logger.trace("Cleared SecurityContextHolder");
        this.logger.trace("Handling authentication failure");
        this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!request.getMethod().equals(AuthConstants.AUTHENTICATION_METHOD)) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }

            InputStream inputStream = request.getInputStream();
            Credentials credentials = this.objectMapper.readValue(inputStream, Credentials.class);

            UserDetails user = userDetailsService.loadUserByUsername(credentials.getUsername());

            if (user != null && passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            } else {
                throw new InternalAuthenticationServiceException("Invalid credentials.");
            }

        } catch (Exception e) {
            throw new InternalAuthenticationServiceException("Authentication failed: " + e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }


}
