package com.sowermate.notification.repositories;

import com.sowermate.notification.entities.Notification;
import com.sowermate.notification.projections.NotificationDropdownProjection;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * <h1>NotificationRepositoryTest</h1>
 * This class is responsible for testing CRUD operations for  Notification entities.
 * It tests HTTP requests.
 * @author pgawade
 * @version 1.0
 * @since 2023-12-02
 */
@Transactional
@SpringBootTest
class NotificationRepositoryTest {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;
    /**
     * To get new Notification entity.
     * */
    private Notification getNotification()
    {
        Notification notification=new Notification();
        notification.setUuid(UUID.randomUUID().toString());
        notification.setNotificationTypeId(1L);
        notification.setSubject("Install");
        notification.setMessage("Installed");
        notification.setIsActive(true);
        notification.setCreatedBy("priti");
        notification.setLastUpdatedBy("priti");
        return notification;
    }

    /**
     * Test for finding all Notification by its UUID and NotificationType ID.
     */
    @Test
    void findNotificationByUuidAndNotificationTypeId() {
        Notification notification=getNotification();
        Notification actualResult=notificationRepository.save(notification);
        Notification expectedResult=this.notificationRepository.findNotificationByUuidAndNotificationTypeId(actualResult.getUuid(), actualResult.getNotificationTypeId());
        assertEquals(actualResult.getSubject(),expectedResult.getSubject());
    }
    /**
     * Test for finding all active Notification by its UUID and NotificationType ID.
     */
    @Test
    void findByIsActive() {
        Notification notification=getNotification();
        List<Notification> actualResult=this.notificationRepository.findByIsActive(notification.getIsActive());
        notificationRepository.save(notification);
        List<Notification>expectedResult=this.notificationRepository.findByIsActive(notification.getIsActive());
        assertThat(actualResult.size()).isEqualTo(expectedResult.size()-1);
    }
    /**
     * Test for finding all Notification by its UUID and NotificationType ID.
     */
    @Test
    void findAllByNotificationTypeId() {
        Notification notification=getNotification();
        List<Notification> actualResult=this.notificationRepository.findAllByNotificationTypeId(notification.getNotificationTypeId());
        notificationRepository.save(notification);
        List<Notification> expectedResult=this.notificationRepository.findAllByNotificationTypeId(notification.getNotificationTypeId());
        assertThat(actualResult.size()).isEqualTo(expectedResult.size()-1);
    }
/**
        * Test for finding all active Notification by its UUID and NotificationType ID.
     */
    @Test
    void findAllByNotificationTypeIdAndIsActive() {
        Notification notification=getNotification();
        List<Notification> actualResult=this.notificationRepository.findAllByNotificationTypeIdAndIsActive(notification.getNotificationTypeId(),notification.getIsActive());
        notificationRepository.save(notification);
        List<Notification> expectedResult=this.notificationRepository.findAllByNotificationTypeIdAndIsActive(notification.getNotificationTypeId(),notification.getIsActive());
        assertThat(actualResult.size()).isEqualTo(expectedResult.size()-1);
    }
    /**
     * Test for finding all Notification by its UUID and NotificationType ID with projection.
     */
    @Test
    void findByNotificationTypeIdAndIsActive() {
        Notification notification=getNotification();
        List<NotificationDropdownProjection> actualResult=this.notificationRepository.findByNotificationTypeIdAndIsActive(notification.getNotificationTypeId(),notification.getIsActive());
        notificationRepository.save(notification);
        List<NotificationDropdownProjection> expectedResult=this.notificationRepository.findByNotificationTypeIdAndIsActive(notification.getNotificationTypeId(), notification.getIsActive());
        assertThat(actualResult.size()).isEqualTo(expectedResult.size()-1);
    }
    /**
     * Test for finding Notification by its UUID and NotificationType ID.
     */

    @Test
    void findByNotificationTypeId() {
        Notification notification=getNotification();
        List<NotificationDropdownProjection> actualResult=this.notificationRepository.findByNotificationTypeId(notification.getNotificationTypeId());
        notificationRepository.save(notification);
        List<NotificationDropdownProjection> expectedResult=this.notificationRepository.findByNotificationTypeId(notification.getNotificationTypeId());
        assertThat(actualResult.size()).isEqualTo(expectedResult.size()-1);
    }
    /**
     * Test for finding Notification by its UUID.
     */
    @Test
    void findIdByUuid() {
        //Given
        Notification notification=getNotification();
        //When
        Notification result=notificationRepository.save(notification);
        //Then
        assertEquals(notification.getId(),result.getId());
    }
}