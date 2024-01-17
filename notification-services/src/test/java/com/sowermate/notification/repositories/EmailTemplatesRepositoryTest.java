package com.sowermate.notification.repositories;


import com.sowermate.notification.entities.EmailTemplates;
import com.sowermate.notification.projections.EmailTemplatesDropdownProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <h1>EmailTemplatesRepositoryTest</h1>
 * This class is responsible for testing CRUD operations for  EmailTemplates entities.
 * It tests HTTP requests .
 *
 * @author ajadhav
 * @version 1.0
 * @since 2023-10-26
 */
@Transactional
@SpringBootTest
class EmailTemplatesRepositoryTest {

    @Autowired
    private EmailTemplatesRepository emailTemplatesRepository;

    /**
     * Test for finding emailTemplates by its uuid.
     */
    @Test
    void findAllByTenantId() {
        EmailTemplates emailTemplates = getEmailTemplates();
        List<EmailTemplates> actualResult = this.emailTemplatesRepository.findAllBy();
        emailTemplatesRepository.save(emailTemplates);
        List<EmailTemplates> expectedResult = emailTemplatesRepository.findAllBy();
        assertThat(actualResult.size()).isEqualTo(expectedResult.size() - 1);
    }

    /**
     * Test for finding all active or inactive emailTemplates.
     */
    @Test
    void findAllByTenantIdAndIsActive() {
        EmailTemplates emailTemplates = getEmailTemplates();
        List<EmailTemplates> actualResult = this.emailTemplatesRepository.findAllByIsActive(emailTemplates.getIsActive());
        emailTemplatesRepository.save(emailTemplates);
        List<EmailTemplates> expectedResult = this.emailTemplatesRepository.findAllByIsActive(emailTemplates.getIsActive());
        assertThat(actualResult.size()).isEqualTo(expectedResult.size() - 1);
    }

    /**
     * Test for finding all emailTemplates with a projection By its active status.
     */
    @Test
    void findByTenantId() {
        EmailTemplates emailTemplates = getEmailTemplates();
        List<EmailTemplatesDropdownProjection> actualResult = this.emailTemplatesRepository.findBy();
        emailTemplatesRepository.save(emailTemplates);
        List<EmailTemplatesDropdownProjection> expectedResult = emailTemplatesRepository.findBy();
        assertThat(actualResult.size()).isEqualTo(expectedResult.size() - 1);
    }

    /**
     * Test for finding all emailTemplates with a projection.
     */
    @Test
    void findByTenantIdAndIsActive() {
        EmailTemplates emailTemplates = getEmailTemplates();
        List<EmailTemplatesDropdownProjection> actualResult = this.emailTemplatesRepository.findByIsActive(emailTemplates.getIsActive());
        emailTemplatesRepository.save(emailTemplates);
        List<EmailTemplatesDropdownProjection> expectedResult = this.emailTemplatesRepository.findByIsActive(emailTemplates.getIsActive());
        assertThat(actualResult.size()).isEqualTo(expectedResult.size() - 1);
    }

    /**
     * Test for finding the ID of a emailTemplates entity by UUID.
     */
    @Test
    void findEmailTemplatesByUuidAndTenantId() {
        EmailTemplates emailTemplates = getEmailTemplates();
        EmailTemplates exceptedResult = emailTemplatesRepository.save(emailTemplates);
        EmailTemplates actualResult = emailTemplatesRepository.findEmailTemplatesByUuid(exceptedResult.getUuid());
        assertThat(actualResult).isEqualTo(exceptedResult);
    }

    /**
     * Test for finding the ID of a emailTemplates entity by UUID.
     */
    @Test
    void findIdByUuid() {
        EmailTemplates emailTemplates = getEmailTemplates();
        EmailTemplates exceptedResult = emailTemplatesRepository.save(emailTemplates);
        Optional<Long> result = emailTemplatesRepository.findIdByUuid(exceptedResult.getUuid());
        Long actualResult = result.get();
        assertThat(actualResult).isEqualTo(exceptedResult.getId());
    }

    /**
     * Test for finding the ID of a emailTemplates entity by UUID.
     */
    @Test
    void findById() {
        EmailTemplates emailTemplates = getEmailTemplates();
        List<EmailTemplatesDropdownProjection> exceptedResult = emailTemplatesRepository.findBy();
        emailTemplatesRepository.save(emailTemplates);
        List<EmailTemplatesDropdownProjection> actualResult = emailTemplatesRepository.findBy();
    }

    /**
     * Creates instance of EmailTemplates with sample data for testing purposes.
     */
    public EmailTemplates getEmailTemplates() {
        EmailTemplates emailTemplates = new EmailTemplates();
        emailTemplates.setUuid(UUID.randomUUID().toString());
        emailTemplates.setName("a");
        emailTemplates.setSubject("b");
        emailTemplates.setContent("m");
        emailTemplates.setIsActive(true);
        emailTemplates.setCreatedBy("anil");
        emailTemplates.setCreatedDateTime(LocalDateTime.now());
        emailTemplates.setLastUpdatedBy("jay");
        emailTemplates.setLastUpdatedDateTime(LocalDateTime.now());
        return emailTemplates;
    }
}