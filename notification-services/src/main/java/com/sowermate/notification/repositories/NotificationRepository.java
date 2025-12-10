package com.sowermate.notification.repositories;

import com.sowermate.notification.entities.Notification;
import com.sowermate.notification.projections.NotificationDropdownProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * <h1>NotificationRepository Interface</h1>
 * This interface extends {@link JpaRepository} and provides custom query methods for the {@link Notification} entity.
 * It serves as the Data Access Object (DAO) in the application for all database interactions related to {@link Notification} instances.
 *
 * @author pgawade
 * @version 1.0
 * @see JpaRepository
 * @see Notification
 * @since 2023-11-24
 */

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    /**
     * Finds a {@link Notification} entity by its UUID and notificationType id.
     * @param notificationTypeId the notificationType id to filter by.
     * @param uuid the UUID of the Notification to retrieve.
     * @return the {@link Notification} entity with the given UUID, or null if not found.
     * @see Notification
     */
    Notification findNotificationByUuidAndNotificationTypeId(@Param("uuid") String uuid, Long notificationTypeId);

    /**
     * Finds a list of {@link Notification} entities based on their 'isActive' status.
     * @param isActive the active status to filter by. True for active Notification, False for inactive.
     * @return a list of {@link Notification} entities with the given active status.
     * @see Notification
     */
    List<Notification> findByIsActive(boolean isActive);

    /**
     * Finds a list of {@link Notification} entities based on their notificationType id.
     * @param notificationTypeId the id to filter by.
     * @return a list of {@link Notification} entities with the given active status.
     * @see Notification
     */
    List<Notification> findAllByNotificationTypeId(Long notificationTypeId);

    /**
     * Finds a list of {@link Notification} entities based on their NotificationType id and 'isActive' status.
     * @param notificationTypeId the notificationType id to filter by.
     * @param isActive the active status to filter by. True for active notification, False for inactive.
     * @return a list of {@link Notification} entities with the given active status.
     * @see Notification
     */
    List<Notification> findAllByNotificationTypeIdAndIsActive(Long notificationTypeId, Boolean isActive);

    /**
     * Finds a list of {@link Notification} entities UUId and their notificationType based on their notificationType id and  'isActive' status.
     * @param notificationTypeId the notificationType id to filter by.
     * @param isActive the active status to filter by. True for active Notification, False for inactive.
     * @return a list of {@link Notification} entities with the given active status.
     * @see Notification
     */
    List<NotificationDropdownProjection> findByNotificationTypeIdAndIsActive(Long notificationTypeId, Boolean isActive);

    /**
     * Finds a list of {@link Notification} entities UUId and their NotificationType based on their notificationType id and  'isActive' status.
     * @param notificationTypeId the NotificationType id to filter by.
     * @return a list of {@link Notification} entities with the given active status.
     * @see Notification
     */

    List<NotificationDropdownProjection> findByNotificationTypeId(Long notificationTypeId);

    /**
     * Finds the ID of  Notification entity in the database based on its UUID.
     * @param uuid the UUID of the Notification for which the ID is sought.
     * @return an {@link Optional} containing the ID of the Notification if found, or an empty {@link Optional} if not found.
     */

    @Query("select n.id from Notification n where n.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") String uuid);
}
