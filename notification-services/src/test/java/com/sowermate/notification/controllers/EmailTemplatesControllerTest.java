package com.sowermate.notification.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sowermate.notification.dtos.EmailTemplatesDto;
import com.sowermate.notification.services.EmailTemplatesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h1>EmailTemplatesControllerTest</h1>
 * This class is responsible for testing CRUD operations for EmailTemplates entities.
 * It tests HTTP requests .
 *
 * @author ajadhav
 * @version 1.0
 * @since 2023-10-26
 */
@WebMvcTest(EmailTemplatesController.class)
class EmailTemplatesControllerTest {

    @MockBean
    private EmailTemplatesService emailTemplatesService;
    @Autowired
    private MockMvc mockMvc;

    /**
     * Test case for creating a request.
     */
    @Test
    void createEmailTemplate() throws Exception{
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        when(emailTemplatesService.createEmailTemplate(emailTemplatesDto)).thenReturn(emailTemplatesDto);
        mockMvc.perform(post("http://localhost:8080/emailTemplates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(emailTemplatesDto)))
                .andExpect(status().isCreated());
    }

    /**
     * Test case for updating a request.
     */
    @Test
    void updateEmailTemplate() throws Exception{
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        EmailTemplatesDto employeeDetailsDto1 = new EmailTemplatesDto();
        employeeDetailsDto1.setCreatedBy("abhay");
        when(emailTemplatesService.createEmailTemplate(emailTemplatesDto)).thenReturn(emailTemplatesDto);
        mockMvc.perform(put("http://localhost:8080/emailTemplates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employeeDetailsDto1)))
                .andExpect(status().isOk());
    }

    /**
     * Test case for getEmailTemplates a request.
     */
    @Test
    void getEmailTemplateByUuidAndTenantId() throws Exception {
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        when(emailTemplatesService.createEmailTemplate(emailTemplatesDto)).thenReturn(emailTemplatesDto);
        mockMvc.perform(get("http://localhost:8080/emailTemplates/178d2680-a643-47b1-a501-42dbd6968614?tenantUuid=00bc11e0-1821-4c98-b80f-c6934da3ae85&jobTitleUuid=c58abe8f-0a52-4651-bcf5-c3d94736eba7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test case for getAllEmailTemplates a request.
     */
    @Test
    void getAllEmailTemplates() throws Exception{
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        when(emailTemplatesService.createEmailTemplate(emailTemplatesDto)).thenReturn(emailTemplatesDto);
        mockMvc.perform(get("http://localhost:8080/emailTemplates?tenantUuid=c76664e9-d7e8-490e-854a-391bba2ed06a&status=active")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test case for getAllDropdown emailTemplates a request.
     */
    @Test
    void getAllEmailTemplatesDropdown() throws Exception{
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        when(emailTemplatesService.createEmailTemplate(emailTemplatesDto)).thenReturn(emailTemplatesDto);
        mockMvc.perform(get("http://localhost:8080/emailTemplates?tenantUuid=c76664e9-d7e8-490e-854a-391bba2ed06a&status=active")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test case for deleting a request.
     */
    @Test
    void softDeleteEmailTemplate() throws Exception{
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        when(emailTemplatesService.createEmailTemplate(emailTemplatesDto)).thenReturn(emailTemplatesDto);
        mockMvc.perform(delete("http://localhost:8080/emailTemplates/178d2680-a643-47b1-a501-42dbd6968614?tenantUuid=c76664e9-d7e8-490e-854a-391bba2ed06a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test case for retrieving emailTemplates value.
     */
    public EmailTemplatesDto getEmailTemplatesDto() {
        EmailTemplatesDto emailTemplatesDto = new EmailTemplatesDto();
        emailTemplatesDto.setUuid(UUID.randomUUID().toString());
        emailTemplatesDto.setName("anil");
        emailTemplatesDto.setSubject("biology");
        emailTemplatesDto.setContent("great");
        emailTemplatesDto.setIsActive(true);
        emailTemplatesDto.setCreatedBy("anil");
        emailTemplatesDto.setCreatedDatetime(LocalDateTime.of(2023, 10, 26, 10, 3));
        emailTemplatesDto.setLastUpdatedBy("jay");
        emailTemplatesDto.setLastUpdatedDatetime(LocalDateTime.of(2023, 10, 26, 10, 3));
        return emailTemplatesDto;
    }

}