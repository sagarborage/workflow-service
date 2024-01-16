package com.sowermate.security.services;

import com.sowermate.security.projections.UserAuthSuccessDetailsProjection;
import com.sowermate.user.entities.UserAuth;
import com.sowermate.user.entities.UserRole;
import com.sowermate.user.repositories.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserAuthRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsServiceImpl(UserAuthRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public UserAuth registerUser(String username, String password, UserRole role) {
        UserAuth user = new UserAuth();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPasswordHash(), user.getIsActive(), user.getIsAccountNonExpired(), user.getIsCredentialsNonExpired(),
                user.getIsAccountNonLocked(), Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()))
        );
    }

    @Override
    public UserAuth findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
/*
    @Override
    public UserAuthSuccessDetailsProjection findUserAuthSuccessDetails(String username) {
        return userRepository.getUserAuthSuccessDetailsProjectionByUsername(username);
    }*/
}
