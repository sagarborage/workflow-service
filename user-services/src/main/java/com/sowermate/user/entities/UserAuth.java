package com.sowermate.user.entities;

import com.sowermate.base.entities.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/**
 * <h1> UserAuth Class</h1>
 * This class represents a UserAuth entity in the application. It extends the {@link Base} class
 * which provides common entity fields such as UUID, creation and update timestamps.
 *
 * @author asalunkhe
 * @version 1.0
 * @see Base
 * @since 2023-11-20
 */
@Getter
@Setter
@Entity
@Table(name = "user_auth")
public class UserAuth extends Base {

    /**
     * Represents the unique identifier for a party.
     * This field stores the unique identifier associated with a party table in the system.
     */
    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String phone;

    /**
     * The username of user
     */
    @Column
    private String username;

    /**
     * The hash value of password.
     */
    @Column(name = "password_hash")
    private String passwordHash;

    /**
     * The user is verified or not.
     */
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    /**
     * The email of user is verified or not.
     */
    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    /**
     * The email of user is verified or not.
     */
    @Column(name = "is_phone_verified")
    private Boolean isPhoneVerified;

    @Column(name = "is_account_non_expired")
    private Boolean isAccountNonExpired;

    @Column(name = "is_account_non_locked")
    private Boolean isAccountNonLocked;

    @Column(name = "is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;

    @Column(name = "failed_attempt")
    private Long failedAttempt;


}
