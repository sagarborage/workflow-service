package com.sowermate.notification.controllers;


import com.sowermate.notification.dtos.EmailRequestDto;
import com.sowermate.notification.services.EmailRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>EmailRequestController</h1>
 * This class is responsible for managing CRUD operations for EmailRequest entities.
 * It handles HTTP requests and responses and utilizes EmailRequestService for business logic.
 *
 * @author asalunkhe
 * @version 1.0
 * @since 2023-12-07
 */
@RestController
@RequestMapping("/emailRequests")
public class EmailRequestController {
    @Autowired
    private EmailRequestService emailRequestService;

    /**
     * Controller method to send a welcome email.
     *
     * @param emailRequestDto The email request data, including recipient information.
     * @return A {@link ResponseEntity} with a success message if the email is sent successfully.
     */
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendWelcomeEmail(@RequestBody EmailRequestDto emailRequestDto) {
        emailRequestService.sendEmailWithTemplate(emailRequestDto);
        return ResponseEntity.ok("Email sent to " + emailRequestDto.getToEmail());
    }

    /**
     * Controller method to send an email with an attachment.
     *
     * @param emailRequestDto The email request data, including recipient information and attachment details.
     * @return A success message if the email with attachment is sent successfully, or an error message if sending fails.
     */
    @PostMapping("/sendEmailAttachment")
    public String sendEmailWithAttachment(@RequestBody EmailRequestDto emailRequestDto) {
        try {
            emailRequestService.sendEmailWithTemplateAndAttachment(emailRequestDto);
            return "Email with attachment sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email with attachment";
        }
    }
}
