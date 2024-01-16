package com.sowermate.notification.dtos;

import com.sowermate.flexipunch.dtos.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>EmailTemplatesDto Class</h1>
 * This Data Transfer Object (DTO) class represents a EmailTemplates object for use in API calls.
 * It extends the {@link BaseDto} class which provides common fields such as UUID.
 *
 * @author asalunkhe
 * @version 1.0
 * @see BaseDto
 * @since 2023-12-07
 */
@Getter
@Setter
public class EmailTemplatesDto extends BaseDto {

    /**
     * The name of the email template.
     */
    private String name;

    /**
     * The subject of the email template.
     */
    private String subject;

    /**
     * The content of the email template.
     */
    private String content;

    /**
     * The type of the email template.
     */
    private String emailTemplateCode;

}
