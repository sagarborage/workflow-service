package com.sowermate.security.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        String userName = "SAGAR";
        String passwordSimple = "2610";
        String password = bpe.encode(passwordSimple);
        boolean isMatching = bpe.matches(passwordSimple, password);

        System.out.println("Username: " + userName +
                ", Password: " + passwordSimple +
                ", Encrypted: " + password +
                ", IsMatching: " + isMatching);
    }
}
