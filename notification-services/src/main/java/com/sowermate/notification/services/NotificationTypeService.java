package com.sowermate.notification.services;


import com.sowermate.notification.dtos.NotificationTypeDto;

import java.util.List;
import java.util.Map;

/**
 * @author pgawade
 * @version 1.0
 * @Priti <h1>NotificationTypeService Interface</h1>
 * Provides the blueprint for NotificationType-related operations which include
 * creation, updates, retrieval, and soft deletion of NotificationType entities.
 * @see {@link NotificationTypeDto}
 * @since 2023-11-24
 */
public interface NotificationTypeService {
    /**
     * Creates a new party NotificationType.
     * @param notificationTypeDto {@link NotificationTypeDto} object containing NotificationType details.
     * @return {@link NotificationTypeDto} object representing the newly created NotificationType entity.
     */
    NotificationTypeDto createNotificationType(NotificationTypeDto notificationTypeDto);

    /**
     * Updates an existing NotificationType entity.
     * @param notificationTypeDto {@link NotificationTypeDto object containing the updated NotificationType details.
     * @return {@link NotificationTypeDto} object representing the updated NotificationType entity.
     */
    NotificationTypeDto updateNotificationType(NotificationTypeDto notificationTypeDto);

    /**
     * Retrieves a NotificationType entity by its UUID.
     * @param notificationTypeUuid The UUID string of the NotificationType entity.
     * @return {@link NotificationTypeDto} object representing the retrieved NotificationType entity.
     */
    NotificationTypeDto getNotificationTypeByUuid(String notificationTypeUuid);

    /**
     * Retrieves a list of NotificationType entities based on their status.
     * @param status     The status string to filter NotificationType entities by (e.g., "all", "active", "inactive").
     * @return List of {@link NotificationTypeDto} objects.
     */
    List<NotificationTypeDto> getNotificationTypes(String status);

    /**
     * Retrieves a list of NotificationType entities based on their status.
     * @param status     The status string to filter NotificationType entities by (e.g., "all", "active", "inactive").
     * @return List of {@link NotificationTypeDto} objects.
     */
    List<Map<String, String>> getNotificationTypeDropdown(String status);

    /**
     * Soft deletes a NotificationType entity by setting its status to "inactive".
     * @param notificationTypeUuid The UUID string of the NotificationType entity to be soft-deleted.
     */
    void softDeleteNotificationType(String notificationTypeUuid);
    /**
     * Hard deletes a NotificationType entity by their.
     * @param notificationTypeUuid The UUID string of the NotificationType entity to be deleted.
     */
    void hardDeleteNotificationType(String notificationTypeUuid);

    /**
     * Retrieves the ID of a NotificationType based on its UUID.
     * @param notificationTypeUuid The UUID of the NotificationType for which the ID is being retrieved.
     * @return The ID of the NotificationType if found, or throws a ResourceNotFoundException if not found.
     * @throws com.sowermate.base.exceptions.ResourceNotFoundException If the NotificationType with the specified UUID is not found.
     */
    public Long getNotificationTypeId(String notificationTypeUuid);
}
