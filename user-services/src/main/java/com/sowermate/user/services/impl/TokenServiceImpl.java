package com.sowermate.user.services.impl;

import com.sowermate.notification.dtos.EmailRequestDto;
import com.sowermate.notification.entities.EmailContentType;
import com.sowermate.notification.services.EmailRequestService;
import com.sowermate.user.entities.VerificationToken;
import com.sowermate.user.repositories.VerificationTokenRepository;
import com.sowermate.user.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import static com.sowermate.notification.constants.EmailConstants.*;
import static com.sowermate.notification.constants.UrlConstants.EMAIL_CONFIRMATION_LINK;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private EmailRequestService emailRequestService;

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Boolean generateVerificationToken(String email, Long userId) {
        String token = generateToken();
        LocalDateTime tokenTime = LocalDateTime.now().plusHours(24);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setCreatedAT(LocalDateTime.now());
        verificationToken.setExpiredAt(tokenTime);
        verificationToken.setTokenValue(token);
        verificationToken.setUserId(userId);
        this.verificationTokenRepository.save(verificationToken);
        emailRequestService.sendEmailWithTemplate(getEmailRequestDto(email, token));
        return true;
    }

    private EmailRequestDto getEmailRequestDto(String email, String token) {
        HashMap<String, String> map = new HashMap<>();
        map.put(CODE, token);
        map.put(LINK, String.format(EMAIL_CONFIRMATION_LINK, token));
        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setToEmail(email);
        emailRequestDto.setEmailContentType(EmailContentType.HTML);
        emailRequestDto.setParams(map);
        emailRequestDto.setEmailTemplateCode(EMAIL_VERIFICATION_TEMPLATE_CODE);
        return emailRequestDto;
    }
}
