package com.sowermate.security.services;

import com.sowermate.security.config.AuthConstants;
import com.sowermate.security.config.JwtConfig;
import com.sowermate.security.config.value.JwtResponse;
import com.sowermate.security.projections.UserAuthSuccessDetailsProjection;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static com.sowermate.security.config.AuthConstants.EMPTY_STRING;

@Service
public class CustomTokenService {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    public void populateAdditionalProperties(String username, JwtResponse jwtResponse) {
        UserAuthSuccessDetailsProjection userAuthProfile = customUserDetailsService.findUserAuthSuccessDetails(username);
        if (!ObjectUtils.isEmpty(userAuthProfile)) {
            jwtResponse.setUserUuid(userAuthProfile.getUserUuid());
            jwtResponse.setFirstName(userAuthProfile.getFirstName());
            jwtResponse.setLastName(userAuthProfile.getLastName());
            jwtResponse.setUsername(username);
            jwtResponse.setUserPhoneNumber(userAuthProfile.getPhone());
            jwtResponse.setCompanyUuid(userAuthProfile.getCompanyUuid());
            jwtResponse.setCompanyName(userAuthProfile.getCompanyName());
            jwtResponse.setCompanyAddress(userAuthProfile.getCompanyAddress());
            jwtResponse.setAccountNonExpired(userAuthProfile.getIsAccountNonExpired());
            jwtResponse.setAccountNonLocked(userAuthProfile.getIsAccountNonLocked());
        }
    }

    public void generateAccessToken(JwtResponse jwtResponse) {
        jwtResponse.setAccessTokenExpiry(new Date(System.currentTimeMillis() + this.jwtConfig.getAccessTokenExpiry()));
        String accessToken = Jwts.builder()
                .setSubject(jwtResponse.getUsername())
                .claim(AuthConstants.ROLES, jwtResponse.getRoles())
                .claim(AuthConstants.FULL_NAME, jwtResponse.getFirstName() + " " + jwtResponse.getLastName())
                .setExpiration(jwtResponse.getAccessTokenExpiry())
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
        jwtResponse.setAccessToken(accessToken);
    }
    public void generateRefreshToken(JwtResponse jwtResponse) {
        jwtResponse.setRefreshTokenExpiry(new Date(System.currentTimeMillis() + this.jwtConfig.getRefreshTokenExpiry()));
        String refreshToken = Jwts.builder()
                .setSubject(jwtResponse.getUsername())
                .claim(AuthConstants.ROLES, jwtResponse.getRoles())
                .claim(AuthConstants.FULL_NAME, jwtResponse.getFirstName() + " " + jwtResponse.getLastName())
                .setExpiration(jwtResponse.getRefreshTokenExpiry())
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
        jwtResponse.setRefreshToken(refreshToken);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token.replace(AuthConstants.AUTHORIZATION_PREFIX, EMPTY_STRING)).getBody();
    }

    public List<String> extractRoles(String token) {
        return  extractAllClaims(token).get(AuthConstants.ROLES, ArrayList.class);
    }



    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean isValidToken(String token) {
        final String username = extractUsername(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
