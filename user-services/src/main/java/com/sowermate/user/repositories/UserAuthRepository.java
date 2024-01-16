package com.sowermate.user.repositories;


import com.sowermate.security.projections.UserAuthSuccessDetailsProjection;
import com.sowermate.user.entities.UserAuth;
import com.sowermate.user.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * <h1>UserAuthRepository Interface</h1>
 * This interface extends {@link JpaRepository} and provides custom query methods for the {@link UserAuth} entity.
 * It serves as the Data Access Object (DAO) in the application for all database interactions related to {@link UserAuth} instances.
 *
 * @author asalunkhe
 * @version 1.0
 * @see JpaRepository
 * @see UserAuth
 * @since 2023-11-20
 */
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    /**
     * Retrieves a UserAuth entity based on the provided username.
     *
     * @param username The username associated with the UserAuth entity.
     * @return The UserAuth entity corresponding to the provided username, or null if not found.
     */
    UserAuth findByUsername(String username);

    /**
     * Retrieves a UserAuth entity based on the provided UUID.
     *
     * @param uuid The UUID associated with the UserAuth entity.
     * @return The UserAuth entity corresponding to the provided UUID, or null if not found.
     */
    UserAuth findUserAuthByUuid(String uuid);


    /**
     * Retrieves the ID of a UserAuth entity based on the provided UUID.
     *
     * @param uuid The UUID associated with the UserAuth entity.
     * @return An Optional containing the ID of the UserAuth entity corresponding to the provided UUID,
     * or an empty Optional if not found.
     */
    @Query("select ua.id from UserAuth ua where ua.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") String uuid);

    /**
     * Finds the UUID (Universally Unique Identifier) associated with the specified username (email).
     *
     * @param email The username (email) for which to retrieve the UUID.
     * @return An {@code Optional<String>} containing the UUID corresponding to the given username,
     * or an empty optional if no such user exists.
     */
    @Query("select ua.uuid from UserAuth ua where ua.username = :email")
    Optional<String> findUuidByUsername(@Param("email") String email);

    /**
     * Retrieves the UserAuth entity associated with the specified user ID.
     *
     * @param id The unique identifier of the user.
     */
    Optional<UserAuth> findUserAuthById(Long id);

    /**
     * Finds the UUID (Universally Unique Identifier) associated with the specified username (email).
     *
     * @param email The username (email) for which to retrieve the ID.
     * @return An {@code Optional<String>} containing the ID corresponding to the given username,
     * or an empty optional if no such user exists.
     */
    @Query("select ua.id from UserAuth ua where ua.username = :email")
    Optional<Long> findIdByUsername(@Param("email") String email);


    String USER_AUTH_QUERY = "SELECT ed.firstName AS firstName, " +
            "ed.lastName AS lastName, " +
            "ed.phone AS phone, " +
            "ct.iso2Code AS iso2Code, " +
            "ua.isAccountNonExpired AS isAccountNonExpired, " +
            "ua.uuid AS userUuid, " +
            "ua.isAccountNonLocked AS isAccountNonLocked, " +
            "ua.isCredentialsNonExpired AS isCredentialsNonExpired, " +
            "ua.failedAttempt AS failedAttempt, " +
            "t.uuid AS companyUuid, " +
            "t.tenantName AS companyName, " +
            "t.address AS companyAddress " +
            "FROM Tenant t " +
            "JOIN Countries ct ON ct.id = t.countryId " +
            "JOIN UserAuth ua ON ua.tenantId = t.id  " +
            "JOIN EmployeeDetails ed ON ed.userId = ua.id  " +
            "WHERE ua.username = :username ";

    @Query(USER_AUTH_QUERY)
    UserAuthSuccessDetailsProjection getUserAuthSuccessDetailsProjectionByUsername(@Param("username") String username);

    @Query("SELECT ua.role from UserAuth ua where ua.id = :userId")
    Optional<UserRole> getRoleByUserId(@Param("userId") Long userId);
}
