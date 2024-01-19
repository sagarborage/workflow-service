package com.sowermate.notification.services.impl;


import com.sowermate.notification.dtos.EmailRequestDto;
import com.sowermate.notification.entities.EmailContentType;
import com.sowermate.notification.entities.EmailTemplates;
import com.sowermate.notification.services.EmailRequestService;
import com.sowermate.notification.services.EmailTemplatesService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

;

/**
 * <h1>EmailRequestServiceImpl Class</h1>
 * Implementation of the {@link EmailRequestService} interface.
 *
 * @author asalunke
 * @version 1.0
 * @see EmailRequestService
 * @since 2023-12-07
 */
@Service
public class EmailRequestServiceImpl implements EmailRequestService {
    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendEmailWithTemplate(EmailRequestDto emailRequestDto) {
        EmailTemplates emailTemplates = emailTemplatesService.getEmailTemplateByTemplateCode(emailRequestDto.getEmailTemplateCode());
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(emailRequestDto.getToEmail());

            String subject = emailTemplates.getSubject();
            for (Map.Entry<String, String> entry : emailRequestDto.getParams().entrySet()) {
                subject = subject.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
            helper.setSubject(subject);

            String content = emailTemplates.getContent();
            for (Map.Entry<String, String> entry : emailRequestDto.getParams().entrySet()) {
                content = content.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }

            if (emailRequestDto.getEmailContentType() == EmailContentType.HTML) {
                helper.setText(content, true);
            } else {
                helper.setText(content, false);
            }

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendEmailWithTemplateAndAttachment(EmailRequestDto emailRequestDto) {
        EmailTemplates emailTemplates = emailTemplatesService.getEmailTemplateByTemplateCode(emailRequestDto.getEmailTemplateCode());
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(emailRequestDto.getToEmail());

            String subject = emailTemplates.getSubject();
            for (Map.Entry<String, String> entry : emailRequestDto.getParams().entrySet()) {
                subject = subject.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
            helper.setSubject(subject);

            String content = emailTemplates.getContent();
            for (Map.Entry<String, String> entry : emailRequestDto.getParams().entrySet()) {
                content = content.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
            if (emailRequestDto.getEmailContentType() == EmailContentType.HTML) {
                helper.setText(content, true);
            } else {
                helper.setText(content, false);
            }

           /* if (emailRequestDto.getAttachmentFilePath() != null) {
                FileSystemResource file = new FileSystemResource(emailRequestDto.getAttachmentFilePath());
                helper.addAttachment(file.getFilename(), file);
            }*/

           helper.addAttachment(emailRequestDto.getAttachmentFileName(),new ByteArrayResource(emailRequestDto.getBytes()));

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sendEmail(String to, String subject, String text) {
        boolean flag = false;
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            javaMailSender.send(mimeMessage);
            flag = true;


        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return flag;
    }

}
