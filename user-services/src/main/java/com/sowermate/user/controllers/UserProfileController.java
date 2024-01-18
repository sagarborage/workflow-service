package com.sowermate.user.controllers;

import com.sowermate.base.common.constants.StatusConstants;
import com.sowermate.base.exceptions.ApiResponse;
import com.sowermate.user.dtos.UserProfileDto;
import com.sowermate.user.services.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <h1>UserProfileController</h1>
 * This class is responsible for managing CRUD operations for UserProfile entities.
 * It handles HTTP requests and responses and utilizes UserProfileService for business logic.
 *
 * @author asalunkhe
 * @version 1.0
 * @since 2023-11-24
 */

@RestController
@RequestMapping("/userProfiles")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    /**
     * Creates a new user profile.
     *
     * @param userProfileDto The UserProfileDto containing the details of the user profile to be created.
     * @return ResponseEntity<UserProfileDto> The created user profile along with the HTTP status code.
     */
    @PostMapping
    public ResponseEntity<UserProfileDto> createUserProfile(@Valid @RequestBody UserProfileDto userProfileDto) {
        UserProfileDto create = this.userProfileService.createUserProfile(userProfileDto);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user profile.
     *
     * @param userProfileDto The UserProfileDto containing the updated details of the user profile.
     * @return ResponseEntity<UserProfileDto> The updated user profile along with the HTTP status code.
     */
    @PutMapping("/{tenantUuid}")
    public ResponseEntity<UserProfileDto> updateUserProfile(@PathVariable String tenantUuid, @Valid @RequestBody UserProfileDto userProfileDto) {
        UserProfileDto update = this.userProfileService.updateUserProfile(tenantUuid, userProfileDto);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    /**
     * Retrieves a specific user profile based on the provided UUID and user UUID.
     *
     * @param tenantUuid     The tenant UUID.
     * @param userUuid The UUID of the user associated with the profile.
     * @return ResponseEntity<UserProfileDto> The retrieved user profile along with the HTTP status code.
     */
    @GetMapping("/{tenantUuid}/{userUuid}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String tenantUuid, @PathVariable String userUuid) {
        UserProfileDto userProfileDto = this.userProfileService.getUserProfile(tenantUuid, userUuid);
        return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
    }


    /**
     * Retrieves all user profiles based on the status.
     *
     * @param status The status of the profiles to retrieve (default is StatusConstants.ALL).
     * @return ResponseEntity<List < UserProfileDto>> The list of retrieved user profiles along with the HTTP status code.
     */
    @RequestMapping(value = "/{tenantUuid}", method = RequestMethod.GET)
    public ResponseEntity<List<UserProfileDto>> getAllUserProfile(@PathVariable String tenantUuid, @RequestParam(name = StatusConstants.REQUEST_PARAM_STATUS, defaultValue = StatusConstants.ALL) String status) {
        List<UserProfileDto> userProfileDtoList = this.userProfileService.getAllUserProfile(tenantUuid, status);
        return new ResponseEntity<>(userProfileDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves a dropdown list of user profiles based on the status.
     *
     * @param status The status of the profiles to include in the dropdown (default is StatusConstants.ALL).
     * @return ResponseEntity<List < Map < String, String>>> The dropdown list of user profiles along with the HTTP status code.
     */
    @GetMapping("/dropdown")
    public ResponseEntity<List<Map<String, String>>> getAllUserProfileDropdown(@RequestParam(name = StatusConstants.REQUEST_PARAM_STATUS, defaultValue = StatusConstants.ALL) String status) {
        List<Map<String, String>> userProfileDropdown = this.userProfileService.getAllUserProfileDropdown(status);
        return new ResponseEntity<>(userProfileDropdown, HttpStatus.OK);
    }

    /**
     * Soft deletes a user profile based on the provided UUID and user UUID.
     *
     * @param tenantUuid     The tenant uuid.
     * @param userUuid The UUID of the user associated with the profile.
     * @return ResponseEntity<ApiResponse> The response indicating the success of the soft deletion along with the HTTP status code.
     */
    @DeleteMapping("/{tenantUuid}/{userUuid}")
    public ResponseEntity<ApiResponse> softDeleteUserProfile(@PathVariable String tenantUuid, @PathVariable String userUuid) {
        this.userProfileService.softDeleteUserProfile(tenantUuid, userUuid);
        return new ResponseEntity<>(new ApiResponse("User profile deleted successfully", true), HttpStatus.OK);
    }

}

