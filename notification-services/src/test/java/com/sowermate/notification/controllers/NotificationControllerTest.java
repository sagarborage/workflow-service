package com.sowermate.notification.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sowermate.notification.dtos.NotificationDto;
import com.sowermate.notification.services.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h1>NotificationControllerTest</h1>
 * This class is responsible for testing CRUD operations for Notification entities.
 * It tests HTTP requests.
 * @author pgawade
 * @version 1.0
 * @since 2023-12-03
 */
@WebMvcTest(NotificationController.class)
class NotificationControllerTest {
    @MockBean
    NotificationService notificationService;
    @Autowired
    MockMvc mockMvc;
    /**
     * Creates instance of Notification with sample data for testing purposes.
     */
    private NotificationDto getNotificationDto()
    {
        NotificationDto notificationDto=new NotificationDto();
        notificationDto.setUuid(UUID.randomUUID().toString());
        notificationDto.setNotificationTypeUuid("75860cfa-90a9-11ee-96d9-ee85cf7f0cbf");
        notificationDto.setSubject("Install");
        notificationDto.setMessage("installed");
        notificationDto.setCreatedBy("Priti");
        notificationDto.setIsActive(true);
        notificationDto.setLastUpdatedBy("priti");
        return notificationDto;
    }
    /**
     * Tests the creation of  Notification.
     */
    @Test
    void createNotification() throws Exception {
        NotificationDto notificationDto =getNotificationDto();
        when(notificationService.createNotification(notificationDto)).thenReturn(notificationDto);
        mockMvc.perform(post("http://localhost:8080/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationDto)))
                .andExpect(status().isCreated());
    }
    /**
     * Tests the updation of  Notification by its uuid.
     */
    @Test
    void updateNotification()  throws Exception{
        NotificationDto notificationDto =getNotificationDto();
        NotificationDto forUpdate=new NotificationDto();
        forUpdate.setSubject("Install");
        when(notificationService.updateNotification(notificationDto)).thenReturn(forUpdate);
        mockMvc.perform(put("http://localhost:8080/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationDto)))
                .andExpect(status().isOk());
    }
    /**
     * Tests the finding Notification by its uuid.
     */
    @Test
    void getNotification()  throws Exception{
        NotificationDto notificationDto =getNotificationDto();
        when(notificationService.getNotificationByUuid(notificationDto.getNotificationTypeUuid(), notificationDto.getUuid())).thenReturn(notificationDto);
        mockMvc.perform(get("http://localhost:8080/notification/321e3bb2-3161-4203-a9fd-11d464979c37?notificationTypeUuid=395cfb2c-cd00-4754-91c3-2e2d40166ef7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationDto)))
                .andExpect(status().isOk());
    }
    /**
     * Tests the finding of  list of Notification.
     */
    @Test
    void getAllNotification() throws Exception {
        NotificationDto notificationDto =getNotificationDto();
        List<NotificationDto> notificationDtoList=this.notificationService.getNotifications(notificationDto.getNotificationTypeUuid(),"all");
        when(notificationService.getNotifications(notificationDto.getNotificationTypeUuid(),"all")).thenReturn(notificationDtoList);
        mockMvc.perform(get("http://localhost:8080/notification?notificationTypeUuid=395cfb2c-cd00-4754-91c3-2e2d40166ef7&status=all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationDtoList)))
                .andExpect(status().isOk());
    }
    /**
     * Tests the finding of  list of Notification with projection.
     */
    @Test
    void getNotificationDropdown() throws Exception {
        NotificationDto notificationDto =getNotificationDto();
        List<Map<String,String>> notificationDropdownProjectionList=this.notificationService.getNotificationDropdown(notificationDto.getNotificationTypeUuid(), "all");
        when(notificationService.getNotificationDropdown(notificationDto.getNotificationTypeUuid(), "all")).thenReturn(notificationDropdownProjectionList);
        mockMvc.perform(get("http://localhost:8080/notification/dropdown?notificationTypeUuid=395cfb2c-cd00-4754-91c3-2e2d40166ef7&status=all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationDropdownProjectionList)))
                .andExpect(status().isOk());
    }
    /**
     * Tests the soft deletion of Notification.
     */

    @Test
    void softDeleteRequest() throws Exception {
        NotificationDto notificationDto =getNotificationDto();
        mockMvc.perform(delete("http://localhost:8080/notification/soft/321e3bb2-3161-4203-a9fd-11d464979c37?notificationTypeUuid=395cfb2c-cd00-4754-91c3-2e2d40166ef7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationDto)))
                .andExpect(status().isOk());
    }

    @Test
    void hardDeleteRequest() {
    }
}