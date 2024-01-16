package com.sowermate.notification.controllers;


import com.sowermate.flexipunch.common.constants.StatusConstants;
import com.sowermate.flexipunch.exceptions.ApiResponse;
import com.sowermate.notification.dtos.NotificationTypeDto;
import com.sowermate.notification.services.NotificationTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <h1>NotificationTypeController</h1>
 * This class is responsible for managing CRUD operations for NotificationType entities.
 * It handles HTTP requests and responses and utilizes NotificationType for business logic.
 * @author pgawade
 * @version 1.0
 * @since 2023-11-24
 */

@RestController
@RequestMapping("/notificationtype")
public class NotificationTypeController {
    @Autowired
    private NotificationTypeService notificationTypeService;

    /**
     * Creates a new NotificationType.
     *
     * @param notificationTypeDto the {@link NotificationTypeDto} object containing the details of the Party to be created.
     * @return the newly created {@link NotificationTypeDto} object.
     */
    @PostMapping
    public ResponseEntity<NotificationTypeDto> createNotificationType(@Valid  @RequestBody NotificationTypeDto notificationTypeDto) {
        NotificationTypeDto createdNotificationType = this.notificationTypeService.createNotificationType(notificationTypeDto);
        return new ResponseEntity<NotificationTypeDto>(createdNotificationType, HttpStatus.CREATED);

    }

    /**
     * Updates an existing NotificationType.
     *
     * @param notificationTypeDto the {@link NotificationTypeDto} object containing the updated details.
     * @return the updated {@link NotificationTypeDto} object.
     */
    @PutMapping
    public ResponseEntity<NotificationTypeDto> updateNotificationType(@Valid @RequestBody NotificationTypeDto notificationTypeDto) {
        NotificationTypeDto updatedNotificationType = this.notificationTypeService.updateNotificationType(notificationTypeDto);
        return new ResponseEntity<NotificationTypeDto>(updatedNotificationType, HttpStatus.OK);
    }

    /**
     * Retrieves a NotificationType by their UUID.
     *
     * @param notificationTypeUuid The UUID of the desired NotificationType.
     * @return the {@link NotificationTypeDto} object.
     */
    @GetMapping("/{notificationTypeUuid}")
    public ResponseEntity<NotificationTypeDto> getNotificationType(@PathVariable String notificationTypeUuid) {
        NotificationTypeDto notificationTypeDto = this.notificationTypeService.getNotificationTypeByUuid(notificationTypeUuid);

        return new ResponseEntity<NotificationTypeDto>(notificationTypeDto, HttpStatus.OK);
    }

    /**
     * Retrieves all NotificationTypes, optionally filtering by their status i.e. all, active, inactive.
     *
     * @param status Optional filter for NotificationType status i.e. all, active, inactive.
     * @return List of {@link NotificationTypeDto} objects.
     */
    @GetMapping
    public ResponseEntity<List<NotificationTypeDto>> getAllNotificationType(@RequestParam(name = StatusConstants.REQUEST_PARAM_STATUS, defaultValue = StatusConstants.ALL) String status) {
        List<NotificationTypeDto> notificationTypeList = this.notificationTypeService.getNotificationTypes(status);
        return new ResponseEntity<List<NotificationTypeDto>>(notificationTypeList, HttpStatus.OK);

    }

    /**
     * Retrieves all NotificationTypes, optionally filtering by their status i.e. all, active, inactive.
     *
     * @param status Optional filter for NotificationType status i.e. all, active, inactive.
     * @return List of {@link NotificationTypeDto} objects UUID and notification type.
     */
    @GetMapping("/dropdown")
    public ResponseEntity<List<Map<String, String>>> getNotificationTypeDropdown(@RequestParam(name = StatusConstants.REQUEST_PARAM_STATUS, defaultValue = StatusConstants.ALL) String status) {
        List<Map<String, String>> notificationTypeDropdownList = this.notificationTypeService.getNotificationTypeDropdown(status);
        return new ResponseEntity<List<Map<String, String>>>(notificationTypeDropdownList, HttpStatus.OK);
    }

    /**
     * Soft-deletes a NotificationType by setting their active status to false.
     *
     * @param notificationTypeUuid The UUID of the NotificationType to be soft-deleted.
     * @return ResponseEntity with the status of the operation.
     */
    @DeleteMapping("/soft/{notificationTypeUuid}")
    public ResponseEntity<ApiResponse> softDeleteRequest(@PathVariable String notificationTypeUuid) {
        this.notificationTypeService.softDeleteNotificationType(notificationTypeUuid);
        return new ResponseEntity<>(new ApiResponse("NotificationType Deleted successfully", true), HttpStatus.OK);
    }

    /**
     * Hard-deletes a NotificationType by their uuid.
     *
     * @param notificationTypeUuid The UUID of the NotificationType to be deleted.
     */

    @DeleteMapping("/hard/{notificationTypeUuid}")
    public ResponseEntity<ApiResponse> hardDeleteRequest(@PathVariable String notificationTypeUuid) {
        this.notificationTypeService.hardDeleteNotificationType(notificationTypeUuid);
        return new ResponseEntity<>(new ApiResponse("NotificationType Deleted successfully", true), HttpStatus.OK);
    }

}
