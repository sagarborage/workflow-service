package com.sowermate.notification.projections;

/**
 * <h1>EmailTemplatesDropdownProjection Interface</h1>
 * The EmailTemplatesDropdownProjection interface defines a projection for extracting specific attributes
 * from the EmailTemplates entity to be used in dropdown lists or selection interfaces.
 *
 * @author asalunkhe
 * * @version 1.0
 * * @see Base
 * * @since 2023-12-07
 */
public interface EmailTemplatesDropdownProjection {
    /**
     * Gets the UUID (Universally Unique Identifier) of the email template.
     *
     * @return The UUID of the email template.
     */
    String getUuid();

    /**
     * Gets the name of the email template.
     *
     * @return The name of the email template.
     */
    String getName();

}
