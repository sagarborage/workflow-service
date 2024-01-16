package com.sowermate.notification.entities;

import com.sowermate.base.entities.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


/**
 * <h1>EmailTemplates Class</h1>
 * This class represents a EmailTemplates entity in the application. It extends the {@link Base} class
 * which provides common entity fields such as UUID, creation and update timestamps.
 *
 * @author asalunkhe
 * @version 1.0
 * @see Base
 * @since 2023-12-07
 */
@Getter
@Setter
@Entity
@Table(name = "email_templates")
public class EmailTemplates extends Base {

    /**
     * The name of the email template.
     */
    @Column(name = "name")
    private String name;

    /**
     * The subject of the email template.
     */
    @Column(name = "subject")
    private String subject;

    /**
     * The content of the email template.
     */
    @Column(name = "content")
    private String content;

    /**
     * The type of the email template.
     */
    @Column(name = "template_code")
    private String emailTemplateCode;
}