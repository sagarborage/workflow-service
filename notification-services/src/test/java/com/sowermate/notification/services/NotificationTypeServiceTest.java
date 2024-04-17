package com.sowermate.notification.services;

import com.sowermate.notification.dtos.NotificationTypeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <h1>NotificationTypeServiceTest</h1>
 * This class is responsible for testing CRUD operations for NotificationType entities.
 * It tests HTTP requests.
 * @author pgawade
 * @version 1.0
 * @since 2023-12-03
 */
@Transactional
@SpringBootTest
class NotificationTypeServiceTest {
    @Autowired
    private NotificationTypeService notificationTypeService;

    /**
     * Creates instance of NotificationType with sample data for testing purposes.
     */
    private NotificationTypeDto getNotificationTypeDto()
    {
        NotificationTypeDto notificationTypeDto =new NotificationTypeDto();
        notificationTypeDto.setTypeName("Abc");
        notificationTypeDto.setDescription("abc");
        notificationTypeDto.setIconUrl("www.pic.com");
        notificationTypeDto.setIsActive(true);
        notificationTypeDto.setCreatedBy("priti");
        notificationTypeDto.setLastUpdatedBy("priti");
        return notificationTypeDto;
    }
    /**
     * Tests the creation of NotificationType.
     */
    @Test
    void createNotificationType() {
        NotificationTypeDto exceptedResult = getNotificationTypeDto();
        NotificationTypeDto actualResult = notificationTypeService.createNotificationType(exceptedResult);
        assertThat(exceptedResult.getTypeName()).isEqualTo(actualResult.getTypeName());
    }
    /**
     * Tests the update of NotificationType.
     */
    @Test
    void updateNotificationType() {
        NotificationTypeDto notificationTypeDto = getNotificationTypeDto();
        NotificationTypeDto actualResult = notificationTypeService.createNotificationType(notificationTypeDto);
        actualResult.setDescription("abc");
        NotificationTypeDto exceptedResult = notificationTypeService.updateNotificationType(actualResult);
        assertThat(exceptedResult.getDescription()).isEqualTo("abc");
    }
    /**
     * Tests the finding of NotificationType by its uuid.
     */
    @Test
    void getNotificationTypeByUuid() {
        NotificationTypeDto notificationTypeDto = getNotificationTypeDto();
        NotificationTypeDto expected = this.notificationTypeService.createNotificationType(notificationTypeDto);
        NotificationTypeDto actual = this.notificationTypeService.getNotificationTypeByUuid(expected.getUuid());
        assertThat(expected.getDescription()).isEqualTo(actual.getDescription());
    }
    /**
     * Tests the finding of list of NotificationType by its uuid.
     */
    @Test
    void getNotificationTypes() {
        NotificationTypeDto notificationTypeDto = getNotificationTypeDto();
        List<NotificationTypeDto> exceptedResult = notificationTypeService.getNotificationTypes("active");
        notificationTypeService.createNotificationType(notificationTypeDto);
        List<NotificationTypeDto> actualResult = notificationTypeService.getNotificationTypes("active");
        assertThat(exceptedResult.size()).isEqualTo(actualResult.size() - 1);
    }
    /**
     * Tests the finding of list of NotificationType with projection.
     */
    @Test
    void getNotificationTypeDropdown() {
        NotificationTypeDto notificationTypeDto = getNotificationTypeDto();
        List<Map<String, String>> exceptedResult = notificationTypeService.getNotificationTypeDropdown("active");
        notificationTypeService.createNotificationType(notificationTypeDto);
        List<Map<String, String>> actualResult = notificationTypeService.getNotificationTypeDropdown("active");
        assertThat(exceptedResult.size()).isEqualTo(actualResult.size() - 1);
    }
    /**
     * Tests the deletion of NotificationType.
     */
    @Test
    void softDeleteNotificationType() {
        NotificationTypeDto notificationTypeDto = getNotificationTypeDto();
        NotificationTypeDto actualResult = notificationTypeService.createNotificationType(notificationTypeDto);
        notificationTypeService.softDeleteNotificationType(actualResult.getUuid());
        NotificationTypeDto exceptedResult = notificationTypeService.getNotificationTypeByUuid(notificationTypeDto.getUuid());
        assertThat(exceptedResult.getIsActive()).isEqualTo(false);
    }

   /* @Test
    void hardDeleteNotificationType() {
    }*/
    /**
     * Tests the finding of NotificationType by its ID.
     */
    @Test
    void getNotificationTypeId() {
        NotificationTypeDto notificationTypeDto = getNotificationTypeDto();
        NotificationTypeDto actualResult = notificationTypeService.createNotificationType(notificationTypeDto);
        Long id = notificationTypeService.getNotificationTypeId(actualResult.getUuid());
        assertThat(id).isNotNull();

    }
}