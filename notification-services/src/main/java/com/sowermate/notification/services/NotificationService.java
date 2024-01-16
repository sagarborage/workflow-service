package com.sowermate.notification.services;


import com.sowermate.flexipunch.exceptions.ResourceNotFoundException;
import com.sowermate.notification.dtos.NotificationDto;
import com.sowermate.notification.entities.Notification;

import java.util.List;
import java.util.Map;

/**
 * @author pgawade
 * @version 1.0
 * @Priti <h1>NotificationService Interface</h1>
 * Provides the blueprint for Notification-related operations which include
 * creation, updates, retrieval, and soft deletion of Notification entities.
 * @see {@link Notification}
 * @since 2023-11-05
 */

public interface NotificationService {
    /**
     * Creates a new Notification entity.
     * @param notificationDto {@link NotificationDto} object containing Notification details.
     * @return {@link NotificationDto} object representing the newly created Notification entity.
     */
    NotificationDto createNotification(NotificationDto notificationDto);

    /**
     * Updates an existing Notification entity.
     * @param notificationDto {@link NotificationDto} object containing the updated Notification details.
     * @return {@link NotificationDto} object representing the updated Notification entity.
     */
    NotificationDto updateNotification(NotificationDto notificationDto);

    /**
     * Retrieves a Notification entity by its UUID.
     * @param notificationTypeUuid
     * @param notificationUuid The UUID string of the Notification entity.
     * @return {@link NotificationDto} object representing the retrieved Notification entity.
     */
    NotificationDto getNotificationByUuid(String notificationUuid,String notificationTypeUuid);

    /**
     * Retrieves a list of Notification entities based on their status.
     * @param notificationTypeUuid UUId of NotificationType entity
     * @param status     The status string to filter Notification entities by (e.g., "all", "active", "inactive").
     * @return List of {@link NotificationDto} objects.
     */
    List<NotificationDto> getNotifications(String notificationTypeUuid, String status);

    /**
     * Retrieves a list of Notification entities UUID and NotificationType based on their status.
     * @param notificationTypeUuid
     * @param status     The status string to filter Notification entities by (e.g., "all", "active", "inactive").
     * @return List of {@link NotificationDto} objects.
     */
    List<Map<String, String>> getNotificationDropdown(String notificationTypeUuid, String status);

    /**
     * Soft deletes a Notification entity by setting its status to "inactive".
     * @param notificationTypeUuid
     * @param notificationUuid The UUID string of the Notification entity to be soft-deleted.
     */
    void softDeleteNotification(String notificationUuid, String notificationTypeUuid);
    /**
     * Hard deletes a Notification entity by their uuid.
     * @param notificationUuid The UUID string of the Notification entity to be deleted.
     */
    void hardDeleteNotification(String notificationUuid, String notificationTypeUuid);

    /**
     * Retrieves the ID of a Notification based on its UUID.
     * @param notificationUuid The UUID of the Notification for which the ID is being retrieved.
     * @return The ID of the Notification if found, or throws a ResourceNotFoundException if not found.
     * @throws ResourceNotFoundException If the party with the specified UUID is not found.
     */
    public Long getNotificationId(String notificationUuid);
}
