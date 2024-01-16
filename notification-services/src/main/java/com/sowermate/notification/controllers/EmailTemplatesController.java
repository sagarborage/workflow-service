package com.sowermate.notification.controllers;


import com.sowermate.flexipunch.common.constants.StatusConstants;
import com.sowermate.flexipunch.exceptions.ApiResponse;
import com.sowermate.notification.dtos.EmailTemplatesDto;
import com.sowermate.notification.services.EmailTemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <h1>EmailTemplatesController</h1>
 * This class is responsible for managing CRUD operations for EmailTemplates entities.
 * It handles HTTP requests and responses and utilizes EmailTemplatesService for business logic.
 *
 * @author asalunkhe
 * @version 1.0
 * @since 2023-12-07
 */
@RestController
@RequestMapping("/emailTemplates")
public class EmailTemplatesController {
    @Autowired
    private EmailTemplatesService emailTemplatesService;

    /**
     * Create a new email template by providing the data in the request body.
     *
     * @param emailTemplatesDto The EmailTemplatesDto containing the information for the new email template.
     * @return ResponseEntity<EmailTemplatesDto> representing the created email template and an HTTP status of 201 (Created).
     */
    @PostMapping
    public ResponseEntity<EmailTemplatesDto> createEmailTemplate(@RequestBody EmailTemplatesDto emailTemplatesDto) {
        EmailTemplatesDto templatesDto = this.emailTemplatesService.createEmailTemplate(emailTemplatesDto);
        return new ResponseEntity<>(templatesDto, HttpStatus.CREATED);
    }

    /**
     * Update an existing email template by providing the updated data in the request body.
     *
     * @param emailTemplatesDto The EmailTemplatesDto containing the updated information for the email template.
     * @return ResponseEntity<EmailTemplatesDto> representing the updated email template and an HTTP status of 200 (OK).
     */
    @PutMapping
    public ResponseEntity<EmailTemplatesDto> updateEmailTemplate(@RequestBody EmailTemplatesDto emailTemplatesDto) {
        EmailTemplatesDto templatesDto = this.emailTemplatesService.updateEmailTemplate(emailTemplatesDto);
        return new ResponseEntity<>(templatesDto, HttpStatus.OK);
    }

    /**
     * Retrieve a list of email templates for a specific party and status.
     *
     * @param status The status of email templates to filter by (e.g., active, inactive).
     * @return ResponseEntity<List < EmailTemplatesDto>> representing the matching email templates and an HTTP status of 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<EmailTemplatesDto>> getAllEmailTemplates(@RequestParam(name = StatusConstants.REQUEST_PARAM_STATUS, defaultValue = StatusConstants.ALL) String status) {
        List<EmailTemplatesDto> emailTemplatesDtoList = this.emailTemplatesService.getAllEmailTemplates(status);
        return new ResponseEntity<>(emailTemplatesDtoList, HttpStatus.OK);
    }

    /**
     * Retrieve a list of email templates in a dropdown format for a specific party and status.
     *
     * @param status The status of email templates to filter by (e.g., active, inactive).
     * @return ResponseEntity<List < Map < String, String>> representing email templates in a dropdown format and an HTTP status of 200 (OK).
     */
    @GetMapping("/dropdown")
    public ResponseEntity<List<Map<String, String>>> getAllEmailTemplatesDropdown(@RequestParam(name = StatusConstants.REQUEST_PARAM_STATUS, defaultValue = StatusConstants.ALL) String status) {
        List<Map<String, String>> emailTemplatesDtoList = this.emailTemplatesService.getAllEmailTemplatesDropdown(status);
        return new ResponseEntity<>(emailTemplatesDtoList, HttpStatus.OK);
    }

    /**
     * Retrieve an email template by its UUID and the associated party.
     *
     * @param uuid The unique identifier (UUID) of the email template.
     * @return ResponseEntity<EmailTemplatesDto> representing the email template and an HTTP status of 200 (OK).
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<EmailTemplatesDto> getEmailTemplateByUuidAndPartyId(@PathVariable String uuid) {
        EmailTemplatesDto emailTemplatesDtoList = this.emailTemplatesService.getEmailTemplateByUuidAndPartyId(uuid);
        return new ResponseEntity<>(emailTemplatesDtoList, HttpStatus.OK);
    }

    /**
     * Soft delete an email template by its UUID and the associated party.
     *
     * @param uuid The unique identifier (UUID) of the email template to be soft-deleted.
     * @return ResponseEntity<ApiResponse> indicating the result of the deletion operation and an HTTP status of 200 (OK).
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse> softDeleteEmailTemplate(@PathVariable String uuid) {
        this.emailTemplatesService.softDeleteEmailTemplate(uuid);
        return new ResponseEntity<>(new ApiResponse("Email Template Deleted !!", true), HttpStatus.OK);
    }

}
