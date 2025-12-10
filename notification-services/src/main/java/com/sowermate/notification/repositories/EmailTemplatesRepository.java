package com.sowermate.notification.repositories;

import com.sowermate.notification.entities.EmailTemplates;
import com.sowermate.notification.projections.EmailTemplatesDropdownProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * <h1>EmailTemplatesRepository Interface</h1>
 * This interface extends {@link JpaRepository} and provides custom query methods for the {@link EmailTemplates} entity.
 * It serves as the Data Access Object (DAO) in the application for all database interactions related to {@link EmailTemplates} instances.
 *
 * @author asalunkhe
 * @version 1.0
 * @see JpaRepository
 * @see EmailTemplates
 * @since 2023-12-07
 */
public interface EmailTemplatesRepository extends JpaRepository<EmailTemplates, Long> {
    /**
     * Retrieve a list of email templates for a specific party.
     *
     * @return A list of EmailTemplates associated with the specified party.
     */
    List<EmailTemplates> findAllBy();

    /**
     * Retrieve a list of active or inactive email templates for a specific party.
     *
     * @param isActive A boolean indicating whether to filter for active (true) or inactive (false) templates.
     * @return A list of EmailTemplates matching the party and active status.
     */
    List<EmailTemplates> findAllByIsActive(Boolean isActive);

    /**
     * Retrieve a list of email templates in a dropdown format for a specific tenant.
     *
     * @return A list of EmailTemplates in a dropdown projection for the specified tenant.
     */
    List<EmailTemplatesDropdownProjection> findBy();

    /**
     * Retrieve a list of active or inactive email templates in a dropdown format for a specific tenant.
     *
     * @param isActive A boolean indicating whether to filter for active (true) or inactive (false) templates.
     * @return A list of EmailTemplates in a dropdown projection matching the tenant and active status.
     */
    List<EmailTemplatesDropdownProjection> findByIsActive(Boolean isActive);


    /**
     * Find an email template by its UUID and the associated party.
     *
     * @param uuid The unique identifier (UUID) of the email template.
     * @return The EmailTemplates entity that matches the provided UUID and party.
     */
    EmailTemplates findEmailTemplatesByUuid(String uuid);

    /**
     * Find the database identifier (ID) of an email template by its UUID.
     *
     * @param uuid The unique identifier (UUID) of the email template.
     * @return An optional containing the ID if found, or empty if the template with the given UUID doesn't exist.
     */
    @Query("select et.id from EmailTemplates et where et.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") String uuid);

    /**
     * Retrieves an {@link Optional} containing an {@link EmailTemplates} entity based on the provided email template code.
     *
     * @param emailTemplateCode The code identifying the email template to be retrieved.
     * @return An {@link Optional} containing the found {@link EmailTemplates} entity, or an empty {@link Optional} if no matching entity is found.
     */
    Optional<EmailTemplates> findByEmailTemplateCode(String emailTemplateCode);
}
