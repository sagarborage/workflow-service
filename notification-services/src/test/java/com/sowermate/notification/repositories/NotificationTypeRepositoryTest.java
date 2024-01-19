package com.sowermate.notification.repositories;

import com.sowermate.notification.entities.NotificationType;
import com.sowermate.notification.projections.NotificationTypeDropdownProjection;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <h1>NotificationTypeRepositoryTest</h1>
 * This class is responsible for testing CRUD operations for  NotificationType entities.
 * It tests HTTP requests.
 * @author pgawade
 * @version 1.0
 * @since 2023-12-02
 */
@Transactional
@SpringBootTest
class NotificationTypeRepositoryTest {

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    /**
     * Creates instance of NotificationType with sample data for testing purposes.
     */
    private NotificationType getNotificationType()
    {
        NotificationType notificationType=new NotificationType();
        notificationType.setUuid(UUID.randomUUID().toString());
        notificationType.setTypeName("Abc");
        notificationType.setDescription("abc");
        notificationType.setIconUrl("www.pic.com");
        notificationType.setIsActive(true);
        notificationType.setCreatedBy("priti");
        notificationType.setLastUpdatedBy("priti");
        return notificationType;
    }

    /**
     * Test for finding NotificationType by its uuid.
     */
    @Test
    void findNotificationTypeByUuid() {
        NotificationType exceptedResult = getNotificationType();
        notificationTypeRepository.save(exceptedResult);
        NotificationType actualResult = notificationTypeRepository.findNotificationTypeByUuid(exceptedResult.getUuid());
        assertThat(exceptedResult.getTypeName()).isEqualTo(actualResult.getTypeName());
    }
    /**
     * Test for finding  all active NotificationType .
     */

       @Test
    void findAllNotificationTypesByIsActive() {
        NotificationType notificationType = getNotificationType();
        List<NotificationType> exceptedResult = notificationTypeRepository.findAllNotificationTypesByIsActive(notificationType.getIsActive());
        notificationTypeRepository.save(notificationType);
        List<NotificationType> actualResult = notificationTypeRepository.findAllNotificationTypesByIsActive(notificationType.getIsActive());
        assertThat(exceptedResult.size()).isEqualTo(actualResult.size() - 1);
    }
    /**
     * Test for finding  all active NotificationType with projection .
     */

    @Test
    void findNotificationTypesByIsActive() {
        NotificationType notificationType = getNotificationType();
        List<NotificationTypeDropdownProjection> exceptedResult = notificationTypeRepository.findNotificationTypesByIsActive(notificationType.getIsActive());
        notificationTypeRepository.save(notificationType);
        List<NotificationTypeDropdownProjection> actualResult = notificationTypeRepository.findNotificationTypesByIsActive(notificationType.getIsActive());
        assertThat(exceptedResult.size()).isEqualTo(actualResult.size() - 1);
    }
    /**
     * Test for finding  all NotificationType .
     */
    @Test
    void findAllBy() {
        NotificationType notificationType = getNotificationType();
        List<NotificationTypeDropdownProjection> exceptedResult = notificationTypeRepository.findAllBy();
        notificationTypeRepository.save(notificationType);
        List<NotificationTypeDropdownProjection> actualResult = notificationTypeRepository.findAllBy();
        assertThat(exceptedResult.size()).isEqualTo(actualResult.size() - 1);
    }
    /**
     * Test for finding NotificationType by its uuid.
     */
    @Test
    void findIdByUuid() {
        NotificationType exceptedResult = getNotificationType();
        notificationTypeRepository.save(exceptedResult);
        Optional<Long> id = notificationTypeRepository.findIdByUuid(exceptedResult.getUuid());
        Long notificationTypeId = id.get();
        assertThat(exceptedResult.getId()).isEqualTo(notificationTypeId);
    }
}