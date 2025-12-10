package com.sowermate.user.projections;

/**
 * <h1>UserProfileDropDownProjection Interface</h1>
 * The UserProfileDropDownProjection interface defines a projection for extracting specific attributes
 * from the UserProfile entity to be used in dropdown lists or selection interfaces.
 *
 * @author asalunkhe
 * @version 1.0
 * @since 2023-11-21
 */
public interface UserProfileDropDownProjection {

    /**
     * Gets the UUID associated with the object.
     *
     * @return A string representing the UUID associated with the object.
     */
    String getUuid();

    /**
     * Gets the first name associated with the object.
     *
     * @return A string representing the first name associated with the object.
     */
    String getFirstName();
}
