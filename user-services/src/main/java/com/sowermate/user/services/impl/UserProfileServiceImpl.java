package com.sowermate.user.services.impl;

import com.sowermate.base.common.constants.DropdownConstants;
import com.sowermate.base.common.constants.StatusConstants;
import com.sowermate.base.exceptions.InvalidInputException;
import com.sowermate.base.exceptions.ResourceNotFoundException;
import com.sowermate.image.config.ImageStorageConfig;
import com.sowermate.image.services.ImageService;
import com.sowermate.notification.dtos.EmailRequestDto;
import com.sowermate.notification.entities.EmailContentType;
import com.sowermate.notification.services.EmailRequestService;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.TenantService;
import com.sowermate.user.dtos.UserProfileDto;
import com.sowermate.user.entities.ConfirmationCode;
import com.sowermate.user.entities.UserAuth;
import com.sowermate.user.entities.UserProfile;
import com.sowermate.user.projections.UserProfileDropDownProjection;
import com.sowermate.user.repositories.ConfirmationCodeRepository;
import com.sowermate.user.repositories.UserAuthRepository;
import com.sowermate.user.repositories.UserProfileRepository;
import com.sowermate.user.services.TokenService;
import com.sowermate.user.services.UserAuthService;
import com.sowermate.user.services.UserProfileService;
import com.sowermate.user.services.UserService;
import com.sowermate.user.utils.ConfirmationCodeUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sowermate.notification.constants.EmailConstants.*;
import static com.sowermate.notification.constants.UrlConstants.ACCOUNT_CONFIRMATION_LINK;

/**
 * <h1>UserProfileServiceImpl Class</h1>
 * Implementation of the {@link UserProfileService} interface.
 *
 * @author asalunkhe
 * @version 1.0
 * @see UserProfileService
 * @since 2023-11-21
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private ImageService imageService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private EmailRequestService emailRequestService;
    @Autowired
    private ConfirmationCodeUtils confirmationCodeUtils;
    @Autowired
    private ConfirmationCodeRepository confirmationCodeRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @Autowired
    private ImageStorageConfig imageStorageConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserProfileDto createUserProfile(UserProfileDto userProfileDto) {
        UserAuth userAuth = this.userAuthService.registerUser(userProfileDto.getEmail(),userProfileDto.getPhone(),userProfileDto.getFirstName(),userProfileDto.getLastName(),userProfileDto.getTenantUuid(),userProfileDto.getRoleUuid(),userProfileDto.getIsActive());
        Long userId = userAuth.getId();
        UserProfile userProfile = this.modelMapper.map(userProfileDto, UserProfile.class);
        userProfile.setUserId(userId);
        this.userProfileRepository.save(userProfile);
        tokenService.generateVerificationToken(userProfileDto.getEmail(), userProfile.getId());
        String confirmationCode = confirmationCodeUtils.generateConfirmationCode();
        EmailRequestDto requestDto = getEmailRequestDto(userProfile, confirmationCode,userProfileDto.getEmail());
        this.emailRequestService.sendEmailWithTemplate(requestDto);
        saveConfirmationCode(userAuth, confirmationCode);
        UserProfileDto userProfileDtoData = this.modelMapper.map(userProfile, UserProfileDto.class);
        userProfileDtoData.setTenantUuid(userProfileDto.getTenantUuid());
        return userProfileDtoData;
    }

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

    private static EmailRequestDto getEmailRequestDto(UserProfile userProfile, String confirmationCode,String email) {
        Map<String, String> map = new HashMap<>();
        map.put(USERNAME, userProfile.getFirstName());
        map.put(LINK, ACCOUNT_CONFIRMATION_LINK);
        map.put(CODE, confirmationCode);
        EmailRequestDto requestDto = new EmailRequestDto();
        requestDto.setToEmail(email);
        requestDto.setEmailTemplateCode(WELCOME_EMAIL_TEMPLATE_CODE);
        requestDto.setEmailContentType(EmailContentType.HTML);
        requestDto.setParams(map);
        return requestDto;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public UserProfileDto updateUserProfile(UserProfileDto userProfileDto) {
        Long userId = this.userAuthService.getUserId(userProfileDto.getUserUuid());
        UserAuth userAuth = this.userAuthService.getUserAuth(userProfileDto.getUserUuid());
        UserProfile userProfile = getUserProfile(userProfileDto.getUuid(), userId);
        if (userProfileDto.getFirstName() != null)
            userProfile.setFirstName(userProfileDto.getFirstName());
        if (userProfileDto.getLastName() != null)
            userProfile.setLastName(userProfileDto.getLastName());
        if (userProfileDto.getEmail() != null) {
            userAuth.setIsEmailVerified(false);
            this.userAuthRepository.save(userAuth);
            this.userService.sendOtp(userProfileDto.getEmail(), userAuth.getId());
        }
        if (userProfileDto.getPhone() != null) {
            userAuth.setIsPhoneVerified(false);
            this.userAuthRepository.save(userAuth);
            this.userService.sendOtp(userProfileDto.getPhone(), userAuth.getId());// TODO:phone SMS work is pending
        }
        if (userProfileDto.getAddress() != null)
            userProfile.setAddress(userProfileDto.getAddress());
        if (userProfileDto.getProfileImg() != null)
            userProfile.setProfileUrl(this.imageService.saveImage(userProfileDto.getProfileImg(), userProfileDto.getUuid(), "front", imageStorageConfig.getProductImagesDirectory()));
        if (userProfileDto.getProfileBackImg() != null)
            userProfile.setProfileBackUrl(this.imageService.saveImage(userProfileDto.getProfileBackImg(), userProfileDto.getUuid(), "back", imageStorageConfig.getProfileImagesDirectory()));
        if (userProfileDto.getBirthDay() != null)
            userProfile.setDob(userProfileDto.getBirthDay());
        if (userProfileDto.getGender() != null)
            userProfile.setGender(userProfileDto.getGender());
        this.userProfileRepository.save(userProfile);
        UserProfileDto userProfileDtoData = this.modelMapper.map(userProfile, UserProfileDto.class);
        userProfileDtoData.setBirthDay(userProfileDto.getBirthDay());
        return userProfileDtoData;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public UserProfileDto getUserProfile(String uuid, String userUuid) {
        Long userId = this.userAuthService.getUserId(userUuid);
        Optional<Long> role = userAuthRepository.getRoleIdByUserId(userId);
        Long userRole = role.get();// TODO:here we need to find role by using role id
        UserProfile userProfile = getUserProfile(uuid, userId);
        UserProfileDto userProfileDto = this.modelMapper.map(userProfile, UserProfileDto.class);
        return userProfileDto;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserProfileDto> getAllUserProfile(String status) {
        List<UserProfile> userProfileList;
        if (StatusConstants.ALL.equalsIgnoreCase(status))
            userProfileList = this.userProfileRepository.findAll();
        else if (StatusConstants.ACTIVE.equalsIgnoreCase(status))
            userProfileList = this.userProfileRepository.findAllByIsActive(true);
        else if (StatusConstants.INACTIVE.equalsIgnoreCase(status))
            userProfileList = this.userProfileRepository.findAllByIsActive(false);
        else
            throw new InvalidInputException("Invalid status!!");
        return userProfileList.stream().map(userProfile -> {
            UserProfileDto userProfileDto = this.modelMapper.map(userProfile, UserProfileDto.class);
            userProfileDto.setBirthDay(userProfile.getDob());
            return userProfileDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserProfileDto> getAllUserProfileByTenant(String tenantUuid) {
        Long tenantId = tenantService.getTenantId(tenantUuid);
        List<UserProfile> userProfileList = userProfileRepository.findAllByTenantId(tenantId);

        return userProfileList.stream().map(userProfile -> {
            UserProfileDto userProfileDto = this.modelMapper.map(userProfile, UserProfileDto.class);
            userProfileDto.setBirthDay(userProfile.getDob());
            return userProfileDto;
        }).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, String>> getAllUserProfileDropdown(String status) {
        List<UserProfileDropDownProjection> userProfileDropDownProjectionList;
        if (StatusConstants.ALL.equalsIgnoreCase(status))
            userProfileDropDownProjectionList = this.userProfileRepository.findBy();
        else if (StatusConstants.ACTIVE.equalsIgnoreCase(status))
            userProfileDropDownProjectionList = this.userProfileRepository.findByIsActive(true);
        else if (StatusConstants.INACTIVE.equalsIgnoreCase(status))
            userProfileDropDownProjectionList = this.userProfileRepository.findByIsActive(false);
        else
            throw new InvalidInputException("Invalid status!!");
        return userProfileDropDownProjectionList.stream().map(userProfileDropDownProjection -> {
            Map<String, String> dropdown = new HashMap<>();
            dropdown.put(DropdownConstants.KEY, userProfileDropDownProjection.getUuid());
            dropdown.put(DropdownConstants.VALUE, userProfileDropDownProjection.getFirstName());
            return dropdown;
        }).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void softDeleteUserProfile(String uuid, String userUuid) {
        Long userId = this.userAuthService.getUserId(userUuid);
        UserAuth userAuth = this.userAuthService.getUserAuth(userUuid);
        userAuth.setIsActive(false);
        this.userAuthRepository.save(userAuth);
        UserProfile userProfile = getUserProfile(uuid, userId);
        userProfile.setIsActive(false);
        this.userAuthRepository.save(userAuth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getUserProfileId(String uuid) {
        return this.userProfileRepository.findIdByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("User profile", "uuid", uuid));
    }

    public UserProfile getUserProfile(String uuid, Long userId) {
        UserProfile userProfile = this.userProfileRepository.findUserProfileByUuidAndUserId(uuid, userId);
        if (userProfile == null)
            throw new ResourceNotFoundException("User profile", "uuid", uuid);
        return userProfile;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String getProfileUuidByUserId(Long userId) {
        return this.userProfileRepository.findUuidByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("UserProfile uuid", "id", String.valueOf(userId)));
    }
}