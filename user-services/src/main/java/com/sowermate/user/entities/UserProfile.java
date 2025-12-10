package com.sowermate.user.entities;

import com.sowermate.base.entities.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * <h1>UserProfile Class</h1>
 * This class represents a UserProfile entity in the application. It extends the {@link Base} class
 * which provides common entity fields such as UUID, creation and update timestamps.
 *
 * @author asalunkhe
 * @version 1.0
 * @see Base
 * @since 2023-11-21
 */
@Getter
@Setter
@Entity
@Table(name = "user_profile")
public class UserProfile extends Base {

    /**
     * Represents the unique identifier for a user.
     * This field stores the unique identifier associated with a user auth table in the system.
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * The first name of user.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * The last name of user.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * The address of user.
     */
    @Column
    private String address;

    /**
     * The dob of user.
     */
    @Column
    private Date dob;

    /**
     * The profile image url of user.
     */
    @Column(name = "profile_url")
    private String profileUrl;

    /**
     * The back profile image url of user.
     */
    @Column(name = "profile_back_url")
    private String profileBackUrl;

    /**
     * The gender of user.
     */
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
