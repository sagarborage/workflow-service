package com.sowermate.user.services;


import com.sowermate.user.dtos.UserProfileDto;

import java.util.List;
import java.util.Map;

/**
 * <h1>UserProfileService Interface</h1>
 * Provides the blueprint for UserProfile-related operations which include
 * creation, updates, retrieval, and soft deletion of UserProfile entities.
 *
 * @author asalunkhe
 * @version 1.0
 * @see UserProfileDto
 * @since 2023-11-21
 */
public interface UserProfileService {

    /**
     * Creates a new user profile based on the provided UserProfileDto.
     *
     * @param userProfileDto The UserProfileDto containing the details of the user profile to be created.
     * @return The created user profile as a UserProfileDto.
     */
    UserProfileDto createUserProfile(UserProfileDto userProfileDto);

    /**
     * Updates an existing user profile based on the provided UserProfileDto.
     *
     * @param userProfileDto The UserProfileDto containing the updated details of the user profile.
     * @return The updated user profile as a UserProfileDto.
     */
    UserProfileDto updateUserProfile(UserProfileDto userProfileDto);

    /**
     * Retrieves a specific user profile based on the provided UUID and user UUID.
     *
     * @param uuid     The UUID of the user profile to retrieve.
     * @param userUuid The UUID of the user associated with the profile.
     * @return The retrieved user profile as a UserProfileDto.
     */
    UserProfileDto getUserProfile(String uuid, String userUuid);


    /**
     * Retrieves all user profiles based on the specified status.
     *
     * @param status The status of the user profiles to retrieve.
     * @return A list of user profiles as UserProfileDto objects.
     */
    List<UserProfileDto> getAllUserProfile(String status);

    List<UserProfileDto> getAllUserProfileByTenant(String tenantUuid);

    /**
     * Retrieves user profiles in a dropdown format based on the specified status.
     *
     * @param status The status of the user profiles to include in the dropdown.
     * @return A list of user profiles in dropdown format as maps.
     */
    List<Map<String, String>> getAllUserProfileDropdown(String status);

    /**
     * Soft deletes a user profile based on the provided UUID and user UUID.
     *
     * @param uuid     The UUID of the user profile to soft delete.
     * @param userUuid The UUID of the user associated with the profile.
     */
    void softDeleteUserProfile(String uuid, String userUuid);

    /**
     * Retrieves the ID of a user profile based on its UUID.
     *
     * @param uuid The UUID of the user profile for which to retrieve the ID.
     * @return The ID of the user profile.
     */
    Long getUserProfileId(String uuid);
    String getProfileUuidByUserId(Long userId);

}
