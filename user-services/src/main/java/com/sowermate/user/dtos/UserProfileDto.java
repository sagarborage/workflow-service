package com.sowermate.user.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
import com.sowermate.user.entities.Gender;
import com.sowermate.user.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

/**
 * <h1>UserProfileDto Class</h1>
 * This Data Transfer Object (DTO) class represents a UserProfile object for use in API calls.
 * It extends the {@link BaseDto} class which provides common fields such as UUID.
 *
 * @author asalunkhe
 * @version 1.0
 * @see BaseDto
 * @since 2023-11-24
 */
@Getter
@Setter
public class UserProfileDto extends BaseDto {

    /**
     * The UUID of the party associated with the user Auth.
     */
    private String tenantUuid;
    private String userUuid;

    private String roleUuid;

    /**
     * The first name of user.
     */
    private String firstName;

    /**
     * The last name of user.
     */
    private String lastName;

    /**
     * The email of user.
     */
    private String email;

    /**
     * The phone of user.
     */
    private String phone;

    /**
     * The address of user.
     */
    private String address;

    /**
     * The gender of user.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Gender gender;

    /**
     * The dob of user.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date birthDay;

    /**
     * The profile image url of user.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String profileImg;

    /**
     * The back profile image url of user.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String profileBackImg;

    /**
     * The profile image url of user.
     */
    private String profileUrl;

    /**
     * The back profile image url of user.
     */
    private String profileBackUrl;

}
