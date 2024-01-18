package com.sowermate.user.services.impl;

import com.sowermate.base.exceptions.ResourceNotFoundException;
import com.sowermate.notification.constants.UrlConstants;
import com.sowermate.notification.dtos.EmailRequestDto;
import com.sowermate.notification.entities.EmailContentType;
import com.sowermate.notification.services.EmailRequestService;
import com.sowermate.tenantService.services.TenantService;
import com.sowermate.user.dtos.ChangePasswordDto;
import com.sowermate.user.dtos.ForgotPasswordRequestDto;
import com.sowermate.user.dtos.LoginDto;
import com.sowermate.user.dtos.VerifyAndSetPasswordDto;
import com.sowermate.user.entities.ConfirmationCode;
import com.sowermate.user.entities.UserAuth;
import com.sowermate.user.entities.VerificationToken;
import com.sowermate.user.repositories.ConfirmationCodeRepository;
import com.sowermate.user.repositories.UserAuthRepository;
import com.sowermate.user.repositories.VerificationTokenRepository;
import com.sowermate.user.services.UserProfileService;
import com.sowermate.user.services.UserService;
import com.sowermate.user.utils.ConfirmationCodeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.sowermate.notification.constants.EmailConstants.*;
import static com.sowermate.user.constants.MessageConstants.*;


@Service("userImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private ConfirmationCodeRepository confirmationCodeRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConfirmationCodeUtils confirmationCodeUtils;
    @Autowired
    private EmailRequestService emailRequestService;
    @Autowired
    private TenantService tenantService;
    @Lazy
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Override
    public Boolean emailVerification(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findVerificationTokenByTokenValue(token);
        if (verificationToken.isPresent()) {
            VerificationToken vToken = verificationToken.get();
            Optional<UserAuth> userAuth = userAuthRepository.findById(vToken.getUserId());
            UserAuth auth = userAuth.get();
            auth.setIsEmailVerified(true);
            userAuthRepository.save(auth);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public String verifyAccountAndSetPassword(VerifyAndSetPasswordDto verifyAndSetPasswordDto) {
        Optional<ConfirmationCode> confirmationCode = confirmationCodeRepository.findConfirmationCodeByCode(verifyAndSetPasswordDto.getConfirmationCode());
        if (confirmationCode.isPresent()) {
            ConfirmationCode code = confirmationCode.get();
            UserAuth userAuth = userAuthRepository.findUserAuthById(code.getUserId()).orElseThrow(() -> new ResourceNotFoundException("username", "id", String.valueOf(code.getUserId())));
            if (code.getCode().equals(verifyAndSetPasswordDto.getConfirmationCode()) && LocalDateTime.now().isBefore(code.getExpiresAt())) {
                userAuth.setPasswordHash(passwordEncoder.encode(verifyAndSetPasswordDto.getPassword()));
                userAuth.setIsEnabled(true);
                this.userAuthRepository.save(userAuth);
                code.setIsUsed(true);
                code.setOperation("set_password");
                this.confirmationCodeRepository.save(code);
                return PASSWORD_SET_MESSAGE;
            } else
                return INVALID_CONFIRMATION_CODE_MESSAGE;
        } else
            return INVALID_CONFIRMATION_CODE_MESSAGE;

    }


    @Transactional
    @Override
    public Boolean regenerateCode(String email) {
        Optional<Long> userId = userAuthRepository.findIdByUsername(email);
        Long id = userId.orElseThrow(() -> new ResourceNotFoundException("User", "username", email));
        Optional<ConfirmationCode> confirmationCode = confirmationCodeRepository.findConfirmationCodeByUserId(id);
        if (confirmationCode.isPresent()) {
            String generatedCode = confirmationCodeUtils.generateConfirmationCode();
            this.emailRequestService.sendEmailWithTemplate(getEmailRequestDto(email, generatedCode));
            ConfirmationCode code = confirmationCode.get();
            code.setCode(generatedCode);
            code.setCreatedAt(LocalDateTime.now());
            code.setExpiresAt(LocalDateTime.now().plusMinutes(5));
            code.setResetAttempts(code.getResetAttempts() + 1);
            this.confirmationCodeRepository.save(code);
            return true;
        } else
            return false;
    }

    @Override
    public String changePassword(ChangePasswordDto changePasswordDto) {
        UserAuth userAuth = userAuthRepository.findByUsername(changePasswordDto.getUsername());
        if (userAuth != null) {
            if (!changePasswordDto.getOldPassword().equals(changePasswordDto.getNewPassword())) {
                if (passwordEncoder.matches(changePasswordDto.getOldPassword(), userAuth.getPasswordHash())) {
                    userAuth.setPasswordHash(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                    this.userAuthRepository.save(userAuth);
                    this.emailRequestService.sendEmailWithTemplate(getEmailRequestDto(changePasswordDto.getUsername()));
                    return PASSWORD_CHANGED_MESSAGE;
                } else return CURRENT_PASSWORD_INVALID_MESSAGE;
            } else
                return OLD_PASSWORD_AND_NEW_PASSWORD_SAME_MESSAGE;
        } else return USER_NOT_EXIT_MESSAGE;
    }

    @Override
    public Boolean forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto) {
        this.emailRequestService.sendEmailWithTemplate(getEmailRequestDtoToForgotPassword(forgotPasswordRequestDto.getEmail()));
        return true;
    }

    @Override
    public Boolean setPassword(LoginDto loginDto) {
        UserAuth userAuth = userAuthRepository.findByUsername(loginDto.getUsername());
        if (userAuth != null) {
            userAuth.setPasswordHash(passwordEncoder.encode(loginDto.getPassword()));
            userAuthRepository.save(userAuth);
            return true;
        } else
            return false;
    }


    public EmailRequestDto getEmailRequestDto(String email, String generatedCode) {
        HashMap<String, String> map = new HashMap<>();
        map.put(CODE, generatedCode);
        map.put(LINK, UrlConstants.ACCOUNT_CONFIRMATION_LINK);
        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setToEmail(email);
        emailRequestDto.setEmailTemplateCode(REGENERATE_CODE_TEMPLATE_CODE);
        emailRequestDto.setEmailContentType(EmailContentType.HTML);
        emailRequestDto.setParams(map);
        return emailRequestDto;
    }

    public EmailRequestDto getEmailRequestDto(String email) {
        UserAuth userAuth = userAuthRepository.findByUsername(email);
        HashMap<String, String> map = new HashMap<>();
        map.put(USERNAME, userAuth.getFirstName());
        map.put(LINK, UrlConstants.WEBSITE_HOME_PAGE_LINK);
        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setToEmail(email);
        emailRequestDto.setEmailTemplateCode(CHANGE_PASSWORD_TEMPLATE_CODE);
        emailRequestDto.setEmailContentType(EmailContentType.HTML);
        emailRequestDto.setParams(map);
        return emailRequestDto;
    }

    public EmailRequestDto getEmailRequestDtoToForgotPassword(String email) {
        UserAuth userAuth = userAuthRepository.findByUsername(email);
        HashMap<String, String> map = new HashMap<>();
        map.put(USERNAME, userAuth.getFirstName());
        map.put(LINK, UrlConstants.SET_PASSWORD_PAGE_LINK_FOR_FORGOT_PASSWORD);
        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setToEmail(email);
        emailRequestDto.setEmailTemplateCode(SET_PASSWORD_TEMPLATE_CODE);
        emailRequestDto.setEmailContentType(EmailContentType.HTML);
        emailRequestDto.setParams(map);
        return emailRequestDto;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateOtpAndUpdateOtpStatus(String newEmail, String enteredOtp) {
        Optional<ConfirmationCode> optionalEmailOtp = confirmationCodeRepository.findByCodeAndIsUsedIsFalse(enteredOtp);

        if (optionalEmailOtp.isPresent()) {
            ConfirmationCode confirmationCode = optionalEmailOtp.get();

            if (enteredOtp.equals(confirmationCode.getCode())) {
                if (LocalDateTime.now().isBefore(confirmationCode.getExpiresAt())) {
                    confirmationCode.setIsUsed(true);
                    confirmationCodeRepository.save(confirmationCode);
                    Optional<UserAuth> userAuth = userAuthRepository.findById(confirmationCode.getUserId());
                    UserAuth auth = userAuth.get();
                    auth.setIsEmailVerified(true);
                    auth.setUsername(newEmail);
                    this.userAuthRepository.save(auth);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static EmailRequestDto getEmailRequestDtoData(String email, String otp) {
        Map<String, String> map = new HashMap<>();
        map.put(CODE, otp);
        EmailRequestDto requestDto = new EmailRequestDto();
        requestDto.setToEmail(email);
        requestDto.setEmailTemplateCode(EMAIL_UPDATE_VERIFICATION_TEMPLATE_CODE);
        requestDto.setEmailContentType(EmailContentType.HTML);
        requestDto.setParams(map);
        return requestDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendOtp(String email, Long userId) {
        String otp = confirmationCodeUtils.generateConfirmationCode();
        this.emailRequestService.sendEmailWithTemplate(getEmailRequestDtoData(email, otp));
        ConfirmationCode confirmationCode = new ConfirmationCode();
        confirmationCode.setCode(otp);
        confirmationCode.setCreatedAt(LocalDateTime.now());
        confirmationCode.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        confirmationCode.setIsUsed(false);
        confirmationCode.setUserId(userId);
        confirmationCodeRepository.save(confirmationCode);
    }
}

