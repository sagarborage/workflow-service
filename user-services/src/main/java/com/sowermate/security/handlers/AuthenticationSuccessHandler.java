package com.sowermate.security.handlers;

import com.sowermate.security.config.JwtConfig;
import com.sowermate.security.config.value.JwtResponse;
import com.sowermate.security.projections.UserAuthSuccessDetailsProjection;
import com.sowermate.security.services.CustomTokenService;
import com.sowermate.security.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationSuccessHandler {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomTokenService customTokenService;

    public JwtResponse generateJwtTokens(Authentication authResult) {
        JwtResponse jwtResponse = new JwtResponse();

        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
        UserAuthSuccessDetailsProjection userAuthProfile = customUserDetailsService.findUserAuthSuccessDetails(username);
        if (!ObjectUtils.isEmpty(userAuthProfile)) {

            jwtResponse.setIso2Code(userAuthProfile.getIso2Code());
            jwtResponse.setFirstName(userAuthProfile.getFirstName());
            jwtResponse.setLastName(userAuthProfile.getLastName());
            jwtResponse.setUsername(username);
            jwtResponse.setUserUuid(userAuthProfile.getUserUuid());
            jwtResponse.setUserPhoneNumber(userAuthProfile.getPhone());
            jwtResponse.setCompanyUuid(userAuthProfile.getCompanyUuid());
            jwtResponse.setCompanyName(userAuthProfile.getCompanyName());
            jwtResponse.setCompanyAddress(userAuthProfile.getCompanyAddress());
            jwtResponse.setAccountNonExpired(userAuthProfile.getIsAccountNonExpired());
            jwtResponse.setAccountNonLocked(userAuthProfile.getIsAccountNonLocked());

        }

        List<String> roles = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        jwtResponse.setRoles(roles);
        customTokenService.generateAccessToken(jwtResponse);
        customTokenService.generateRefreshToken(jwtResponse);

        return jwtResponse;
    }

}
