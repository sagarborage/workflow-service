package com.sowermate.notification.controllers;

import com.sowermate.base.common.constants.StatusConstants;
import com.sowermate.notification.dtos.NotificationDto;
import com.sowermate.notification.services.NotificationService;
import com.sowermate.tenantService.payload.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <h1>NotificationController</h1>
 * This class is responsible for managing CRUD operations for Notification entities.
 * It handles HTTP requests and responses and utilizes Notification for business logic.
 * @author pgawade
 * @version 1.0
 * @since 2023-11-24
 */

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /**
     * Creates a new Notification.
     *
     * @param notificationDto the {@link NotificationDto} object containing the details of the Notification to be created.
     * @return the newly created {@link NotificationDto} object.
     */
    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(@Valid @RequestBody NotificationDto notificationDto) {
        NotificationDto createdNotification = this.notificationService.createNotification(notificationDto);
        return new ResponseEntity<NotificationDto>(createdNotification, HttpStatus.CREATED);

    }

    /**
     * Updates an existing Notification.
     *
     * @param notificationDto the {@link NotificationDto} object containing the updated details.
     * @return the updated {@link NotificationDto} object.
     */
    @PutMapping
    public ResponseEntity<NotificationDto> updateNotification(@Valid @RequestBody NotificationDto notificationDto) {
        NotificationDto updatedNotification = this.notificationService.updateNotification(notificationDto);
        return new ResponseEntity<NotificationDto>(updatedNotification, HttpStatus.OK);
    }

    /**
     * Retrieves a Notification by their UUID.
     *
     * @param notificationTypeUuid The UUID of the desired Notification.
     * @return the {@link NotificationDto} object.
     */
    @GetMapping("/{notificationUuid}")
    public ResponseEntity<NotificationDto> getNotification(@PathVariable String notificationUuid,
                                                           @RequestParam(name = "notificationTypeUuid", required = true) String notificationTypeUuid) {
        NotificationDto notificationDto = this.notificationService.getNotificationByUuid(notificationUuid, notificationTypeUuid);

        return new ResponseEntity<NotificationDto>(notificationDto, HttpStatus.OK);
    }

    /**
     * Retrieves all notifications, optionally filtering by their status i.e. all, active, inactive.
     *
     * @param notificationTypeUuid The UUID of the desired notificationType.
     * @param status               Optional filter for Notification status i.e. all, active, inactive.
     * @return List of {@link NotificationDto} objects.
     */
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotification(@RequestParam(name = "notificationTypeUuid", required = true) String notificationTypeUuid,
                                                                    @RequestParam(name = StatusConstants.REQUEST_PARAM_STATUS, defaultValue = StatusConstants.ALL) String status) {
        List<NotificationDto> notificationList = this.notificationService.getNotifications(notificationTypeUuid, status);
        return new ResponseEntity<List<NotificationDto>>(notificationList, HttpStatus.OK);

    }

    /**
     * Retrieves all notification, optionally filtering by their status i.e. all, active, inactive.
     *
     * @param notificationTypeUuid The UUID of the desired notification.
     * @param status               Optional filter for Notification status i.e. all, active, inactive.
     * @return List of {@link NotificationDto} objects UUID and subject of notification.
     */
    @GetMapping("/dropdown")
    public ResponseEntity<List<Map<String, String>>> getNotificationDropdown(@RequestParam(name = "notificationTypeUuid", required = true) String notificationTypeUuid,
                                                                             @RequestParam(name = StatusConstants.REQUEST_PARAM_STATUS, defaultValue = StatusConstants.ALL) String status) {
        List<Map<String, String>> notificationDropdownList = this.notificationService.getNotificationDropdown(notificationTypeUuid, status);
        return new ResponseEntity<List<Map<String, String>>>(notificationDropdownList, HttpStatus.OK);
    }

    /**
     * Soft-deletes a Notification by setting their active status to false.
     *
     * @param notificationUuid The UUID of the Notification to be soft-deleted.
     * @return ResponseEntity with the status of the operation.
     */

    @DeleteMapping("/soft/{notificationUuid}")
    public ResponseEntity<ApiResponse> softDeleteRequest(@PathVariable String notificationUuid,
                                                         @RequestParam(name = "notificationTypeUuid", required = true) String notificationTypeUuid) {
        this.notificationService.softDeleteNotification(notificationUuid, notificationTypeUuid);


        return new ResponseEntity<>(new ApiResponse("Notification Deleted successfully", true, HttpStatus.OK), HttpStatus.OK);
    }

    /**
     * Hard-deletes a Notification by their uuid.
     *
     * @param notificationUuid The UUID of the Notification to be hard-deleted.
     **/
    @DeleteMapping("/hard/{notificationUuid}")
    public ResponseEntity<ApiResponse> hardDeleteRequest(@PathVariable String notificationUuid,
                                                         @RequestParam(name = "notificationTypeUuid", required = true) String notificationTypeUuid) {
        this.notificationService.hardDeleteNotification(notificationUuid, notificationTypeUuid);


        return new ResponseEntity<>(new ApiResponse("Notification Deleted successfully", true, HttpStatus.OK), HttpStatus.OK);
    }

}
