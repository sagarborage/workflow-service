package com.sowermate.notification.services;

import com.sowermate.notification.dtos.EmailTemplatesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <h1>EmailTemplatesServiceTest</h1>
 * This class is responsible for testing CRUD operations for  EmailTemplates entities.
 * It tests HTTP requests .
 *
 * @author ajadhav
 * @version 1.0
 * @since 2023-10-26
 */
@Transactional
@SpringBootTest
class EmailTemplatesServiceTest {

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    /**
     * Tests the creation the Countries.
     */
    @Test
    void createEmailTemplate() {
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        EmailTemplatesDto expectedResult = emailTemplatesService.createEmailTemplate(emailTemplatesDto);
        assertEquals(emailTemplatesDto.getName(), expectedResult.getName());
    }

    /**
     * Tests the update the EmailTemplates.
     */
    @Test
    void updateEmailTemplate() {
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        EmailTemplatesDto emailTemplateDto = this.emailTemplatesService.createEmailTemplate(emailTemplatesDto);
        emailTemplateDto.setName("anil");
        EmailTemplatesDto emailTemplatesDt = this.emailTemplatesService.updateEmailTemplate(emailTemplateDto);
        assertThat(emailTemplatesDt.getName()).isEqualTo("anil");
    }

    /**
     * Tests the get all the EmailTemplates by its Uuid.
     */
    @Test
    void getAllEmailTemplates() {
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        List<EmailTemplatesDto> emailTemplatesDtoList = this.emailTemplatesService.getAllEmailTemplates( "active");
        this.emailTemplatesService.createEmailTemplate(emailTemplatesDto);
        List<EmailTemplatesDto> emailTemplateDtoList = this.emailTemplatesService.getAllEmailTemplates("active");
        assertThat(emailTemplateDtoList.size() - 1).isEqualTo(emailTemplatesDtoList.size());
    }

    /**
     * Tests the get all EmailTemplates projection by its active status.
     */
    @Test
    void getAllEmailTemplatesDropdown() {
        EmailTemplatesDto employeeDetailsDto = getEmailTemplatesDto();
        List<Map<String, String>> employeeDetailsDtoList = this.emailTemplatesService.getAllEmailTemplatesDropdown("active");
        this.emailTemplatesService.createEmailTemplate(employeeDetailsDto);
        List<Map<String, String>> employeeDetailsDtoList1 = this.emailTemplatesService.getAllEmailTemplatesDropdown( "active");
        assertThat(employeeDetailsDtoList1.size() - 1).isEqualTo(employeeDetailsDtoList.size());
    }

    /**
     * Tests the get all EmailTemplates by its UUID and Tenant ID.
     */
    @Test
    void getEmailTemplateByUuidAndTenantId() {
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        EmailTemplatesDto emailTemplateDto = this.emailTemplatesService.createEmailTemplate(emailTemplatesDto);
        EmailTemplatesDto emailTemplateDt = this.emailTemplatesService.getEmailTemplateByUuidAndPartyId(emailTemplateDto.getUuid());
        assertThat(emailTemplateDt.getName()).isEqualTo(emailTemplateDt.getName());
    }

    /**
     * Tests delete EmailTemplates projection by its Uuid.
     */
    @Test
    void softDeleteEmailTemplate() {
        EmailTemplatesDto emailTemplatesDto = getEmailTemplatesDto();
        EmailTemplatesDto emailTemplateDto = this.emailTemplatesService.createEmailTemplate(emailTemplatesDto);
        this.emailTemplatesService.softDeleteEmailTemplate(emailTemplateDto.getUuid());
        EmailTemplatesDto employeeDetailsDto2 = this.emailTemplatesService.getEmailTemplateByUuidAndPartyId(emailTemplateDto.getUuid());
        assertThat(employeeDetailsDto2.getIsActive()).isEqualTo(false);
    }

    /**
     * Generates an instance of EmailTemplatesDto with sample data for testing purposes.
     */
    public EmailTemplatesDto getEmailTemplatesDto() {
        EmailTemplatesDto emailTemplatesDto = new EmailTemplatesDto();
        emailTemplatesDto.setUuid(UUID.randomUUID().toString());
        emailTemplatesDto.setName("anil");
        emailTemplatesDto.setSubject("java");
        emailTemplatesDto.setContent("language");
        emailTemplatesDto.setIsActive(true);
        emailTemplatesDto.setCreatedBy("anil");
        emailTemplatesDto.setCreatedDatetime(LocalDateTime.of(2023, 10, 26, 10, 3));
        emailTemplatesDto.setLastUpdatedBy("jay");
        emailTemplatesDto.setLastUpdatedDatetime(LocalDateTime.of(2023, 10, 26, 10, 3));
        return emailTemplatesDto;
    }
}
