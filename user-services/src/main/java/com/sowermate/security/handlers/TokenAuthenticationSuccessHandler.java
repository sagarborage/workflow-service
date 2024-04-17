
package com.sowermate.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sowermate.security.config.value.JwtResponse;
import com.sowermate.security.services.CustomTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class handles authentication success scenario.
 * @author sborage
 */
public class TokenAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CustomTokenService customTokenService;

    private final ObjectMapper objectMapper;

    private final CustomResponseHeaderWriter responseHeaderWriter;

    public TokenAuthenticationSuccessHandler(CustomTokenService customTokenService,
                                             ObjectMapper objectMapper,
                                             CustomResponseHeaderWriter responseHeaderWriter) {
        this.customTokenService = customTokenService;
        this.objectMapper = objectMapper;
        this.responseHeaderWriter = responseHeaderWriter;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        this.responseHeaderWriter.writeCommonHeaders(response);
        JwtResponse jwtResponse = this.generateJwtTokens(authentication);
        response.getWriter().write(objectMapper.writeValueAsString(jwtResponse));
        response.getWriter().flush();
        response.getWriter().close();

        this.clearAuthenticationAttributes(request);
    }

    private JwtResponse generateJwtTokens(Authentication authResult) {
        JwtResponse jwtResponse = new JwtResponse();

        String username = ((User) authResult.getPrincipal()).getUsername();
        customTokenService.populateAdditionalProperties(username, jwtResponse);

        List<String> roles = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        jwtResponse.setRoles(roles);
        customTokenService.generateAccessToken(jwtResponse);
        customTokenService.generateRefreshToken(jwtResponse);

        return jwtResponse;
    }


}
