package com.sowermate.user.services.impl;

import com.sowermate.notification.dtos.EmailRequestDto;
import com.sowermate.notification.entities.EmailContentType;
import com.sowermate.notification.services.EmailRequestService;
import com.sowermate.user.entities.ConfirmationCode;
import com.sowermate.user.entities.UserAuth;
import com.sowermate.user.entities.UserRole;
import com.sowermate.user.repositories.ConfirmationCodeRepository;
import com.sowermate.user.services.UserAuthService;
import com.sowermate.user.services.UserProfileService;
import com.sowermate.user.utils.ConfirmationCodeUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.sowermate.notification.constants.EmailConstants.*;
import static com.sowermate.notification.constants.UrlConstants.ACCOUNT_CONFIRMATION_LINK;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserAuthService userAuthService;
   /* @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;*/
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConfirmationCodeUtils confirmationCodeUtils;

    @Autowired
    private ConfirmationCodeRepository confirmationCodeRepository;

    @Autowired
    private EmailRequestService emailRequestService;


    /*@Override
    @Transactional
    public EmployeeDetailsDto createUserProfile(EmployeeDetailsDto employeeDetailsDto) {
        UserAuth userAuth = userAuthService.registerUser(employeeDetailsDto.getPrimaryEmail(), UserRole.user, employeeDetailsDto.getTenantUuid(), employeeDetailsDto.getIsActive(), employeeDetailsDto.getCreatedBy(), employeeDetailsDto.getLastUpdatedBy());
        Long userId = userAuth.getId();
        EmployeeDetails userProfile = this.modelMapper.map(employeeDetailsDto, EmployeeDetails.class);
        userProfile.setUserId(userId);
        userProfile.setUuid(userAuth.getUuid());
        this.employeeDetailsRepository.save(userProfile);

        String confirmationCode = confirmationCodeUtils.generateConfirmationCode();
        EmailRequestDto requestDto = getEmailRequestDto(userProfile, confirmationCode);
        this.emailRequestService.sendEmailWithTemplate(requestDto);

        saveConfirmationCode(userAuth, confirmationCode);
        EmployeeDetailsDto userProfileDtoData = this.modelMapper.map(userProfile, EmployeeDetailsDto.class);
        userProfileDtoData.setUserUuid(userAuth.getUuid());
        userProfileDtoData.setTenantUuid(employeeDetailsDto.getTenantUuid());
        return userProfileDtoData;
    }*/

    private void saveConfirmationCode(UserAuth userAuth, String confirmationCode) {
        ConfirmationCode code = new ConfirmationCode();
        code.setUserId(userAuth.getId());
        code.setCode(confirmationCode);
        code.setCreatedAt(LocalDateTime.now());
        code.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        code.setIsUsed(false);
        code.setResetAttempts(0L);
        confirmationCodeRepository.save(code);
    }

    /*private static EmailRequestDto getEmailRequestDto(EmployeeDetails userProfile, String confirmationCode) {
        Map<String, String> map = new HashMap<>();
        map.put(USERNAME, userProfile.getFirstName());
        map.put(LINK, ACCOUNT_CONFIRMATION_LINK);
        map.put(CODE, confirmationCode);
        EmailRequestDto requestDto = new EmailRequestDto();
        requestDto.setToEmail(userProfile.getEmail());
        requestDto.setEmailTemplateCode(WELCOME_EMAIL_TEMPLATE_CODE);
        requestDto.setEmailContentType(EmailContentType.HTML);
        requestDto.setParams(map);
        return requestDto;
    }

    @Override
    public EmployeeDetailsDto updateUserProfile(EmployeeDetailsDto employeeDetailsDto) {
        return null;
    }*/
}
