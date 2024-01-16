package com.sowermate.user.services;


import com.sowermate.user.entities.UserAuth;
import com.sowermate.user.entities.UserRole;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <h1>UserAuthService Interface</h1>
 * Provides the blueprint for UserAuth-related operations which include.
 *
 * @author asalunkhe
 * @version 1.0
 * @since 2023-11-20
 */
public interface UserAuthService extends UserDetailsService {

    /**
     * Registers a new user with the specified details.
     *
     * @param username      The username for the new user.
     * @param role          The role assigned to the new user.
     * @param isActive      The status indicating whether the user is active.
     * @param createdBy     The username or identifier of the user who created the account.
     * @param lastUpdatedBy The username or identifier of the user who last updated the account.
     * @return The UserAuth entity representing the registered user.
     */
    UserAuth registerUser(String username, UserRole role, String partyUuid, Boolean isActive, String createdBy, String lastUpdatedBy);

    /**
     * Retrieves a UserAuth entity based on the provided username.
     *
     * @param username The username associated with the UserAuth entity.
     * @return The UserAuth entity corresponding to the provided username, or null if not found.
     */
    UserAuth findByUsername(String username);

    /**
     * Retrieves the user ID based on the provided UUID.
     *
     * @param uuid The UUID associated with the user.
     * @return The ID of the user corresponding to the provided UUID, or null if not found.
     */
    Long getUserId(String uuid);

    /**
     * Retrieves the UserAuth entity based on the provided UUID.
     *
     * @param uuid The UUID associated with the UserAuth entity.
     * @return The UserAuth entity corresponding to the provided UUID, or null if not found.
     */
    UserAuth getUserAuth(String uuid);

    /**
     * Retrieves the UUID (Universally Unique Identifier) associated with the specified email.
     *
     * @param email The email address of the user.
     */
    String getUserUuid(String email);
}
