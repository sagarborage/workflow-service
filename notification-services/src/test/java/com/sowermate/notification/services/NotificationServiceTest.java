package com.sowermate.notification.services;

import com.sowermate.notification.dtos.NotificationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
/**
 * <h1>NotificationServiceTest</h1>
 * This class is responsible for testing CRUD operations for Notification entities.
 * It tests HTTP requests.
 * @author pgawade
 * @version 1.0
 * @since 2023-12-03
 */
@Transactional
@SpringBootTest
class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;
    /**
     * Creates instance of Notification with sample data for testing purposes.
     */
    private NotificationDto getNotificationDto()
    {
        NotificationDto notificationDto=new NotificationDto();
        notificationDto.setUuid(UUID.randomUUID().toString());
        notificationDto.setNotificationTypeUuid("3f5365bc-90a9-11ee-96d9-ee85cf7f0cbf");
        notificationDto.setSubject("Install");
        notificationDto.setMessage("installed");
        notificationDto.setCreatedBy("Priti");
        notificationDto.setIsActive(true);
        notificationDto.setLastUpdatedBy("priti");
        return notificationDto;
    }


    /**
     * Tests the creation of Notification.
     */
    @Test
    void createNotification() {
        NotificationDto notificationDto=getNotificationDto();
        NotificationDto expectedResult=notificationService.createNotification(notificationDto);
        assertEquals(notificationDto.getSubject(),expectedResult.getSubject());

    }
    /**
     * Tests the update of Notification.
     */
    @Test
    void updateNotification() {
        NotificationDto notificationDto=getNotificationDto();
        NotificationDto actualResult=notificationService.createNotification(notificationDto);
        NotificationDto forUpdate=notificationService.getNotificationByUuid(notificationDto.getNotificationTypeUuid(), actualResult.getUuid());
        forUpdate.setSubject("Install");
        NotificationDto expectedResult=notificationService.getNotificationByUuid(actualResult.getNotificationTypeUuid(), actualResult.getUuid());
        assertEquals(forUpdate.getSubject(),expectedResult.getSubject());

    }
    /**
     * Tests the finding of Notification by its uuid.
     */
    @Test
    void getNotificationByUuid() {
        NotificationDto notificationDto=getNotificationDto();
        NotificationDto actualResult=notificationService.createNotification(notificationDto);
        NotificationDto expectedResult=notificationService.getNotificationByUuid(actualResult.getNotificationTypeUuid(), actualResult.getUuid());
        assertEquals(actualResult.getSubject(),expectedResult.getSubject());

    }
    /**
     * Tests the finding of list of Notification by its uuid.
     */
    @Test
    void getNotifications() {
        NotificationDto notificationDto=getNotificationDto();
        List<NotificationDto> actualResult=this.notificationService.getNotifications(notificationDto.getNotificationTypeUuid(), "all");
        this.notificationService.createNotification(notificationDto);
        List<NotificationDto> expectedResult=this.notificationService.getNotifications(notificationDto.getNotificationTypeUuid(), "all");
        assertThat(actualResult.size()).isEqualTo(expectedResult.size()-1);

    }
    /**
     * Tests the finding of list of Notification with projection.
     */
    @Test
    void getNotificationDropdown() {
        NotificationDto notificationDto=getNotificationDto();
        List<Map<String,String>> actualResult=this.notificationService.getNotificationDropdown(notificationDto.getNotificationTypeUuid(), "all");
        this.notificationService.createNotification(notificationDto);
        List<Map<String,String>> expectedResult=this.notificationService.getNotificationDropdown(notificationDto.getNotificationTypeUuid(), "all");
        assertThat(actualResult.size()).isEqualTo(expectedResult.size()-1);
    }
    /**
     * Tests the deletion of Notification.
     */
    @Test
    void softDeleteNotification() {
        NotificationDto notificationDto=getNotificationDto();
        NotificationDto actualResult=notificationService.createNotification(notificationDto);
        notificationService.softDeleteNotification(actualResult.getUuid(), actualResult.getNotificationTypeUuid());
        NotificationDto updatedSubject=notificationService.getNotificationByUuid(actualResult.getNotificationTypeUuid(), actualResult.getUuid());
        assertNotNull(updatedSubject);
        assertFalse(updatedSubject.getIsActive());
    }

    @Test
    void hardDeleteNotification() {
    }
    /**
     * Tests the finding of Notification by its ID.
     */
    @Test
    void getNotificationId() {
        NotificationDto notificationDto=getNotificationDto();
        NotificationDto actualResult = notificationService.createNotification(notificationDto);
        Long id = notificationService.getNotificationId(actualResult.getUuid());
        assertThat(id).isNotNull();
    }
}