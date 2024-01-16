package com.sowermate.notification.repositories;

import com.sowermate.notification.entities.NotificationType;
import com.sowermate.notification.projections.NotificationTypeDropdownProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


/**
/* <h1>NotificationTypeRepository Interface</h1>
 * This interface extends {@link JpaRepository} and provides custom query methods for the {@link NotificationType} entity.
 * It serves as the Data Access Object (DAO) in the application for all database interactions related to {@link NotificationType} instances.
 * @author pgawade
 * @version 1.0
 * @see JpaRepository
 * @see NotificationType
 * @since 2023-11-24
 */


public interface NotificationTypeRepository extends JpaRepository<NotificationType,Long> {

    /**
     * Finds a {@link NotificationType} entity by its UUID .
     *
     * @param uuid the UUID of the NotificationType to retrieve.
     * @return the {@link NotificationType} entity with the given UUID, or null if not found.
     * @see NotificationType
     */
    NotificationType findNotificationTypeByUuid(@Param("uuid") String uuid);

    /**
     * Finds a list of {@link NotificationType} entities based on their 'isActive' status.
     *
     * @param isActive the active status to filter by. True for active NotificationType, False for inactive.
     * @return a list of {@link NotificationType} entities with the given active status.
     * @see NotificationType
     */
    List<NotificationType> findByIsActive(boolean isActive);

    /**
     * Finds a list of {@link NotificationType} entities based on their 'isActive' status.
     *
     * @param isActive the active status to filter by. True for active NotificationType, False for inactive.
     * @return a list of {@link NotificationType} entities with the given active status.
     * @see NotificationType
     */
    List<NotificationType> findAllNotificationTypesByIsActive(Boolean isActive);

    /**
     * Finds a list of {@link NotificationType} entities UUId and their NotificationType based on their  'isActive' status.
     * * @param isActive the active status to filter by. True for active NotificationType, False for inactive.
     *
     * @return a list of {@link NotificationType} entities with the given active status.
     * @see NotificationType
     */
    List<NotificationTypeDropdownProjection> findNotificationTypesByIsActive(Boolean isActive);

    /**
     * Finds a list of {@link NotificationType} entities UUId .
     *
     * @return a list of {@link NotificationType} entities with the given active status.
     * @see NotificationType
     */
    List<NotificationTypeDropdownProjection> findAllBy();

    /**
     * Finds the ID of  NotificationType entity in the database based on its UUID.
     *
     * @param uuid the UUID of the NotificationType for which the ID is sought.
     * @return an {@link Optional} containing the ID of the NotificationType if found, or an empty {@link Optional} if not found.
     */
    @Query("select n.id from NotificationType n where n.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") String uuid);


}
