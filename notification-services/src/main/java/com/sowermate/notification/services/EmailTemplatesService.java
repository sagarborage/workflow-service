package com.sowermate.notification.services;

import com.sowermate.notification.dtos.EmailTemplatesDto;
import com.sowermate.notification.entities.EmailTemplates;

import java.util.List;
import java.util.Map;

/**
 * <h1>EmailTemplatesService Interface</h1>
 * Provides the blueprint for EmailTemplates-related operations which include
 * creation, updates, retrieval, and soft deletion of EmailTemplates entities.
 *
 * @author asalunkhe
 * @version 1.0
 * @see EmailTemplatesDto
 * @since 2023-12-07
 */
public interface EmailTemplatesService {

    /**
     * Create a new email template using the provided EmailTemplatesDto.
     *
     * @param emailTemplatesDto The data transfer object containing the information for the new email template.
     * @return The EmailTemplatesDto representing the created email template.
     */
    EmailTemplatesDto createEmailTemplate(EmailTemplatesDto emailTemplatesDto);

    /**
     * Update an existing email template using the provided EmailTemplatesDto.
     *
     * @param emailTemplatesDto The data transfer object containing the updated information for the email template.
     * @return The EmailTemplatesDto representing the updated email template.
     */
    EmailTemplatesDto updateEmailTemplate(EmailTemplatesDto emailTemplatesDto);

    /**
     * Retrieve a list of email templates for a specific party and status.
     *
     * @param status    The status of email templates to filter by (e.g., active, inactive).
     * @return A list of EmailTemplatesDto representing the matching email templates.
     */
    List<EmailTemplatesDto> getAllEmailTemplates(String status);

    /**
     * Retrieve a list of email templates in a dropdown format for a specific party and status.
     *
     * @param status    The status of email templates to filter by (e.g., active, inactive).
     * @return A list of Map objects with key-value pairs representing the email templates in a dropdown format.
     */
    List<Map<String, String>> getAllEmailTemplatesDropdown(String status);

    /**
     * Retrieve an email template by its UUID and the associated party.
     *
     * @param uuid      The unique identifier (UUID) of the email template.
     * @return The EmailTemplatesDto representing the email template with the provided UUID and party.
     */
    EmailTemplatesDto getEmailTemplateByUuidAndPartyId(String uuid);

    /**
     * Soft delete an email template by its UUID and the associated party.
     *
     * @param uuid      The unique identifier (UUID) of the email template to be soft-deleted.
     */
    void softDeleteEmailTemplate(String uuid);

    EmailTemplates getEmailTemplateByTemplateCode(String templateCode);

}
