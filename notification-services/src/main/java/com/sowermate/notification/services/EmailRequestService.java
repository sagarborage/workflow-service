package com.sowermate.notification.services;


import com.sowermate.notification.dtos.EmailRequestDto;

import java.io.IOException;

/**
 * <h1>EmailRequestService Interface</h1>
 * Provides the blueprint for EmailRequest-related operations which include
 * creation, updates, retrieval, and soft deletion of EmailRequest entities.
 *
 * @author asalunkhe
 * @version 1.0
 * @see EmailRequestDto
 * @since 2023-12-07
 */
public interface EmailRequestService {

    /**
     * Sends an email using a predefined email template.
     * <p>
     * This method takes an EmailRequestDto object as input and sends an email using a predefined email
     * template. The email request should contain necessary information such as recipient, subject,
     * message body, and any other required details.
     *
     * @param emailRequestDto An EmailRequestDto object containing the details for sending the email.
     */
    void sendEmailWithTemplate(EmailRequestDto emailRequestDto);

    /**
     * Sends an email using a predefined email template and includes an attachment.
     * <p>
     * This method takes an EmailRequestDto object as input and sends an email using a predefined email
     * template. It also allows attaching a file to the email. The email request should contain necessary
     * information such as recipient, subject, message body, attachment details, and any other required details.
     *
     * @param emailRequestDto An EmailRequestDto object containing the details for sending the email
     *                        with an attachment.
     */
    void sendEmailWithTemplateAndAttachment(EmailRequestDto emailRequestDto) throws IOException;

    /**
     * Sends an email with the specified content to the given recipient.
     *
     * @param to      The email address of the recipient.
     * @param subject The subject of the email.
     * @param text    The content or body of the email.
     * @return {@code true} if the email is sent successfully, {@code false} otherwise.
     */
    boolean sendEmail(String to, String subject, String text);

}
