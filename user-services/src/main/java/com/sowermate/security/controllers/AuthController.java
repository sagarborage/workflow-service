package com.sowermate.security.controllers;

import com.sowermate.security.config.value.JwtResponse;
import com.sowermate.security.dtos.RefreshTokenRequest;
import com.sowermate.security.services.CustomTokenService;
import com.sowermate.security.services.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sowermate.security.config.AuthConstants.ERROR_MESSAGE_TOKEN_EXPIRED;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CustomTokenService customTokenService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAuthenticationToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        try {
            // Validate the refresh token (you may want to add more validation logic)
            if (customTokenService.isValidToken(refreshToken)) {
                String username = customTokenService.extractUsername(refreshToken);
                List<String> roles = customTokenService.extractRoles(refreshToken);

                JwtResponse jwtResponse = new JwtResponse();
                jwtResponse.setRoles(roles);
                customTokenService.populateAdditionalProperties(username, jwtResponse);
                customTokenService.generateAccessToken(jwtResponse);
                customTokenService.generateRefreshToken(jwtResponse);
                // Return the new tokens in the response
                return ResponseEntity.ok(jwtResponse);
            } else {
                // Handle invalid refresh token
                return ResponseEntity.badRequest().body("Invalid refresh token");
            }
        } catch (ExpiredJwtException eje) {
            throw new InternalAuthenticationServiceException(ERROR_MESSAGE_TOKEN_EXPIRED + eje.getMessage());
        } catch (MalformedJwtException eje) {
            throw new InternalAuthenticationServiceException("Invalid Token: " + eje.getMessage());
        } catch (Exception exception) {
            throw new InternalAuthenticationServiceException("Token verification failed: " + exception.getMessage());
        }
    }
}
