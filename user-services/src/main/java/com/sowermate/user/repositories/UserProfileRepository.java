package com.sowermate.user.repositories;


import com.sowermate.user.entities.UserProfile;
import com.sowermate.user.projections.UserProfileDropDownProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * <h1>UserProfileRepository Interface</h1>
 * This interface extends {@link JpaRepository} and provides custom query methods for the {@link UserProfile} entity.
 * It serves as the Data Access Object (DAO) in the application for all database interactions related to {@link UserProfile} instances.
 *
 * @author asalunkhe
 * @version 1.0
 * @see JpaRepository
 * @see UserProfile
 * @since 2023-11-21
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /**
     * Finds a user profile by its UUID and associated user ID.
     *
     * @param tenantId   The tenant id.
     * @param userAuthId The user auth id.
     * @param userProfileUuid The user profile uuid.
     * @return The user profile matching the provided UUID and user ID, or null if not found.
     */
    @Query("select up from UserProfile up " +
            "JOIN UserAuth ua ON ua.id = up.userId " +
            "JOIN TenantEntity te ON ua.tenantId = te.id " +
            "where te.id = :tenantId AND ua.id= :userAuthId AND up.uuid = :userProfileUuid")
    UserProfile findUserProfile(@Param("tenantId") Long tenantId, @Param("userProfileUuid") String userProfileUuid, @Param("userAuthId") Long userAuthId);

    /**
     * Finds all user profiles with the specified activity status.
     *
     * @param tenantId The tenant id.
     * @param isActive The activity status of the user profiles to retrieve.
     * @return A list of user profiles with the specified activity status.
     */
    @Query("select up from UserProfile up " +
            "JOIN UserAuth ua ON ua.id = up.userId " +
            "JOIN TenantEntity te ON ua.tenantId = te.id " +
            "where te.id = :tenantId AND up.isActive = :isActive")
    List<UserProfile> findAllByTenantIdAndIsActive(@Param("tenantId") Long tenantId, @Param("isActive") Boolean isActive);

    /**
     * Finds user profiles in a dropdown projection format.
     *
     * @return A list of user profiles in dropdown projection format.
     */
    List<UserProfileDropDownProjection> findBy();

    /**
     * Finds user profiles in a dropdown projection format with the specified activity status.
     *
     * @param isActive The activity status of the user profiles to retrieve.
     * @return A list of user profiles in dropdown projection format with the specified activity status.
     */
    List<UserProfileDropDownProjection> findByIsActive(Boolean isActive);

    /**
     * Finds the ID of a user profile based on its UUID.
     *
     * @param uuid The UUID of the user profile for which to retrieve the ID.
     * @return An optional containing the ID of the user profile, or empty if not found.
     */
    @Query("select up.id from UserProfile up where up.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") String uuid);

    @Query("select up.uuid from UserProfile up where up.userId = :userId")
    Optional<String> findUuidByUserId(@Param("userId")Long userId);

    Optional<UserProfile> findUserProfileByUserId(Long userId);

    @Query("select up from UserProfile up " +
            "JOIN UserAuth ua ON ua.id = up.userId " +
            "JOIN TenantEntity te ON ua.tenantId = te.id " +
            "where te.id = :tenantId")
    List<UserProfile> findAllByTenantId(@Param("tenantId") Long tenantId);
}
