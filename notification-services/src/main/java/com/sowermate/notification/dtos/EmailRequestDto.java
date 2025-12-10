package com.sowermate.notification.dtos;


import com.sowermate.notification.entities.EmailContentType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


/**
 * <h1>EmailRequestDto Class</h1>
 * This Data Transfer Object (DTO) class represents a EmailRequest object for use in API calls.
 *
 * @author asalunkhe
 * @version 1.0
 * @since 2023-12-07
 */
@Getter
@Setter
public class EmailRequestDto {

    /**
     * The value of the to email.
     */
    private String toEmail;

    /**
     * The value of the email template code.
     */
    private String emailTemplateCode;

    /**
     * The path of the attachment file.
     */
    private String attachmentFileName;

    /**
     * The value of the params.
     */
    private Map<String, String> params;

    /**
     * The type of the email template.
     */
    private EmailContentType emailContentType;

    private String confirmationCode;

    private String link;

    private byte[] bytes;

}
