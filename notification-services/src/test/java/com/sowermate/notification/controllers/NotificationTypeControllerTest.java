package com.sowermate.notification.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sowermate.notification.dtos.NotificationTypeDto;
import com.sowermate.notification.services.NotificationTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h1>NotificationTypeControllerTest</h1>
 * This class is responsible for testing CRUD operations for NotificationType entities.
 * It tests HTTP requests.
 * @author pgawade
 * @version 1.0
 * @since 2023-12-03
 */
@WebMvcTest(NotificationTypeController.class)
class NotificationTypeControllerTest {

    @MockBean
    NotificationTypeService notificationTypeService;
    @Autowired
    MockMvc mockMvc;
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
     * Tests the creation of  NotificationType.
     */
    @Test
    void createNotificationType() throws Exception{
        NotificationTypeDto notificationTypeDto=getNotificationTypeDto();
        when(notificationTypeService.createNotificationType(notificationTypeDto)).thenReturn(notificationTypeDto);
        mockMvc.perform(post("http://localhost:8080/notificationtype")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationTypeDto)))
                .andExpect(status().isCreated());
    }
    /**
     * Tests the updation of  NotificationType by its uuid.
     */
    @Test
    void updateNotificationType() throws Exception{
        NotificationTypeDto notificationTypeDto=getNotificationTypeDto();
        NotificationTypeDto forUpdate=new NotificationTypeDto();
        forUpdate.setTypeName("Abc");
        when(notificationTypeService.updateNotificationType(notificationTypeDto)).thenReturn(forUpdate);
        mockMvc.perform(put("http://localhost:8080/notificationtype")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationTypeDto)))
                .andExpect(status().isCreated());
    }
    /**
     * Tests the finding NotificationType by its uuid.
     */
    @Test
    void getNotificationType() throws Exception {
        NotificationTypeDto notificationTypeDto=getNotificationTypeDto();
        when(notificationTypeService.getNotificationTypeByUuid(notificationTypeDto.getUuid())).thenReturn(notificationTypeDto);
        mockMvc.perform(get("http://localhost:8080/notificationtype/faee8e7b-6b47-4be4-97ae-1de45da3ab56")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationTypeDto)))
                .andExpect(status().isOk());
    }
    /**
     * Tests the finding of  list of NotificationType .
     */

    @Test
    void getAllNotificationType() throws Exception {
        NotificationTypeDto notificationTypeDto=getNotificationTypeDto();
        List<NotificationTypeDto> notificationTypeDtoList=this.notificationTypeService.getNotificationTypes("all");
        when(notificationTypeService.getNotificationTypes("all")).thenReturn(notificationTypeDtoList);
        mockMvc.perform(get("http://localhost:8080/notificationtype?status=all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationTypeDtoList)))
                .andExpect(status().isOk());
    }
    /**
     * Tests the finding of  list of NotificationType with projection.
     */
    @Test
    void getNotificationTypeDropdown() throws Exception {
        NotificationTypeDto notificationTypeDto=getNotificationTypeDto();
        List<Map<String,String>> notificationTypeDropdownProjectionList=this.notificationTypeService.getNotificationTypeDropdown("all");
        when(notificationTypeService.getNotificationTypeDropdown("all")).thenReturn(notificationTypeDropdownProjectionList);
        mockMvc.perform(get("http://localhost:8080/notificationtype/dropdown?status=all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationTypeDropdownProjectionList)))
                .andExpect(status().isOk());
    }
/**
 * Tests the soft deletion of NotificationType.
 */
    @Test
    void softDeleteRequest() throws Exception {
        NotificationTypeDto notificationTypeDto=getNotificationTypeDto();
        mockMvc.perform(delete("http://localhost:8080/subjects/fb91d0b7-eff3-4640-ab87-885ce02a7cb0?classUuid=293e6bb7-c049-4dff-b353-f853feb36b19")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notificationTypeDto)))
                .andExpect(status().isOk());
    }

    @Test
    void hardDeleteRequest() {
    }
}