package com.sowermate.security.filters;

import com.sowermate.base.entities.AuthenticatedUserDetails;
import com.sowermate.security.config.AuthConstants;
import com.sowermate.security.config.UrlConfig;
import com.sowermate.security.handlers.AuthenticationFailureHandler;
import com.sowermate.security.services.CustomTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secret; // Your secret key

    private final UrlConfig urlConfig;

    private final CustomTokenService customTokenService;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public JwtAuthorizationFilter(AuthenticationManager authManager, String secret, UrlConfig urlConfig, AuthenticationFailureHandler authenticationFailureHandler, CustomTokenService customTokenService) {
        super(authManager);
        this.secret = secret;
        this.urlConfig = urlConfig;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.customTokenService = customTokenService;
    }

    private boolean isUnProtectedRequest(String requestUrl) {
        boolean isUnProtectedRequest = false;
        for (String unprotectedUrl: urlConfig.getUnprotectedUrls()) {
            if (requestUrl.contains(unprotectedUrl)) {
                isUnProtectedRequest = true;
                break;
            }
        }

        return isUnProtectedRequest;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String header = request.getHeader(AuthConstants.AUTHORIZATION);

            if (header == null && !isUnProtectedRequest(request.getServletPath()) && !request.getMethod().equals(AuthConstants.OPTIONS)) {
                throw new InternalAuthenticationServiceException("Missing 'Bearer' token in the request");
            } else if (header == null || !header.startsWith(AuthConstants.AUTHORIZATION_PREFIX) || isUnProtectedRequest(request.getServletPath())) {
                chain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.getContextHolderStrategy().clearContext();
            this.logger.error("Failed to process authentication request", authenticationException);
            //this.rememberMeServices.loginFail(request, response);
            this.onUnsuccessfulAuthentication(request, response, authenticationException);
            return;
        }
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        try {
            String token = request.getHeader(AuthConstants.AUTHORIZATION);

            /*if (token != null) {
                Claims claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token.replace(AuthConstants.AUTHORIZATION_PREFIX, EMPTY_STRING))
                        .getBody();
                String username = claims.getSubject();
                List<String> roles = claims.get(AuthConstants.ROLES, ArrayList.class);
                if (username != null) {
                    return new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(new SimpleGrantedAuthority(roles.get(0))));
                }
            }*/

            if (token != null && this.customTokenService.isValidToken(token)) {
                Claims claims = this.customTokenService.extractAllClaims(token);
                String username = claims.getSubject();
                List<String> roles = claims.get(AuthConstants.ROLES, ArrayList.class);
                if (username != null) {
                    AuthenticatedUserDetails userDetails = new AuthenticatedUserDetails(claims.get(AuthConstants.FULL_NAME).toString(),
                            username, true);
                    return new UsernamePasswordAuthenticationToken(userDetails, null, Collections.singleton(new SimpleGrantedAuthority(roles.get(0))));
                }
            }
        } catch (ExpiredJwtException eje) {
            throw new InternalAuthenticationServiceException(AuthConstants.ERROR_MESSAGE_TOKEN_EXPIRED + eje.getMessage());
        } catch (MalformedJwtException eje) {
            throw new InternalAuthenticationServiceException("Invalid Token: " + eje.getMessage());
        } catch (Exception exception) {
            throw new InternalAuthenticationServiceException("Token verification failed: " + exception.getMessage());
        }
        return null;
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}
