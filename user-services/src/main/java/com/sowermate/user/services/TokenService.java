package com.sowermate.user.services;

public interface TokenService {

    Boolean generateVerificationToken(String email,Long userId);

}
