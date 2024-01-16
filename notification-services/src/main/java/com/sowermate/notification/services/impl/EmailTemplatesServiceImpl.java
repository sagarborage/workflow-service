package com.sowermate.notification.services.impl;

import com.sowermate.base.common.constants.DropdownConstants;
import com.sowermate.base.common.constants.StatusConstants;
import com.sowermate.base.exceptions.ResourceNotFoundException;
import com.sowermate.notification.dtos.EmailTemplatesDto;
import com.sowermate.notification.entities.EmailTemplates;
import com.sowermate.notification.projections.EmailTemplatesDropdownProjection;
import com.sowermate.notification.repositories.EmailTemplatesRepository;
import com.sowermate.notification.services.EmailTemplatesService;
import com.sowermate.tenantService.exceptions.InvalidInputException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <h1>EmailTemplatesServiceImpl Class</h1>
 * Implementation of the {@link EmailTemplatesService} interface.
 *
 * @author asalunkhe
 * @version 1.0
 * @see EmailTemplatesService
 * @since 2023-12-07
 */
@Service
public class EmailTemplatesServiceImpl implements EmailTemplatesService {
    @Autowired
    private EmailTemplatesRepository emailTemplatesRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailTemplatesDto createEmailTemplate(EmailTemplatesDto emailTemplatesDto) {
        String content = emailTemplatesDto.getContent();
        content = content.replaceAll("\\\\n", "");
        EmailTemplates emailTemplates = this.modelMapper.map(emailTemplatesDto, EmailTemplates.class);
        emailTemplates.setContent(content);
        this.emailTemplatesRepository.save(emailTemplates);
        return this.modelMapper.map(emailTemplates, EmailTemplatesDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailTemplatesDto updateEmailTemplate(EmailTemplatesDto emailTemplatesDto) {
        String content = emailTemplatesDto.getContent();
        content = content.replaceAll("\\\\n", "");
        EmailTemplates emailTemplates = getEmailTemplates(emailTemplatesDto.getUuid());
        if (emailTemplatesDto.getName() != null)
            emailTemplates.setName(emailTemplatesDto.getName());
        if (emailTemplatesDto.getEmailTemplateCode() != null)
            emailTemplates.setEmailTemplateCode(emailTemplatesDto.getEmailTemplateCode());
        if (emailTemplatesDto.getCreatedBy() != null)
            emailTemplates.setCreatedBy(emailTemplatesDto.getCreatedBy());
        if(content!=null)
            emailTemplates.setContent(content);
        if (emailTemplatesDto.getLastUpdatedBy() != null)
            emailTemplates.setLastUpdatedBy(emailTemplatesDto.getLastUpdatedBy());
        this.emailTemplatesRepository.save(emailTemplates);
        this.modelMapper.map(emailTemplates, emailTemplatesDto);
        return emailTemplatesDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmailTemplatesDto> getAllEmailTemplates(String status) {
        List<EmailTemplates> emailTemplatesList;
        if (StatusConstants.ALL.equals(status.toLowerCase()))
            emailTemplatesList = this.emailTemplatesRepository.findAllBy();
        else if (StatusConstants.ACTIVE.equals(status.toLowerCase()))
            emailTemplatesList = this.emailTemplatesRepository.findAllByIsActive(true);
        else if (StatusConstants.INACTIVE.equals(status.toLowerCase()))
            emailTemplatesList = this.emailTemplatesRepository.findAllByIsActive(false);
        else
            throw new InvalidInputException("Invalid Status!!");
        List<EmailTemplatesDto> emailTemplatesDtoList = emailTemplatesList.stream().map((emailTemplates) -> {
                    EmailTemplatesDto emailTemplatesDto = this.modelMapper.map(emailTemplates, EmailTemplatesDto.class);
                    return emailTemplatesDto;
                }
        ).collect(Collectors.toList());
        return emailTemplatesDtoList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, String>> getAllEmailTemplatesDropdown(String status) {
        List<EmailTemplatesDropdownProjection> emailTemplatesDropdownProjectionList;
        if (StatusConstants.ALL.equals(status.toLowerCase()))
            emailTemplatesDropdownProjectionList = this.emailTemplatesRepository.findBy();
        else if (StatusConstants.ACTIVE.equals(status.toLowerCase()))
            emailTemplatesDropdownProjectionList = this.emailTemplatesRepository.findByIsActive(true);
        else if (StatusConstants.INACTIVE.equals(status.toLowerCase()))
            emailTemplatesDropdownProjectionList = this.emailTemplatesRepository.findByIsActive(false);
        else
            throw new InvalidInputException("Invalid Status!!");
        List<Map<String, String>> keyValueList = emailTemplatesDropdownProjectionList.stream().map((emailTemplatesDropdownProjection -> {
                    Map<String, String> dropdown = new HashMap<>();
                    dropdown.put(DropdownConstants.KEY, emailTemplatesDropdownProjection.getUuid());
                    dropdown.put(DropdownConstants.VALUE, emailTemplatesDropdownProjection.getName());
                    return dropdown;
                }))
                .collect(Collectors.toList());
        return keyValueList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailTemplatesDto getEmailTemplateByUuidAndPartyId(String uuid) {
        EmailTemplates emailTemplates = getEmailTemplates(uuid);
        return this.modelMapper.map(emailTemplates, EmailTemplatesDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void softDeleteEmailTemplate(String uuid) {
        EmailTemplates emailTemplates = getEmailTemplates(uuid);
        emailTemplates.setIsActive(false);
        this.emailTemplatesRepository.save(emailTemplates);
    }

    @Override
    public EmailTemplates getEmailTemplateByTemplateCode(String templateCode) {
        return this.emailTemplatesRepository.findByEmailTemplateCode(templateCode).orElseThrow(() -> new ResourceNotFoundException("Email template", "template code", templateCode));
    }


    /**
     * Retrieve an email template by its UUID and the associated party.
     *
     * @param uuid The unique identifier (UUID) of the email template.
     * @return The EmailTemplates entity representing the email template with the provided UUID and party.
     * @throws ResourceNotFoundException If no email template is found with the specified UUID.
     */
    public EmailTemplates getEmailTemplates(String uuid) {
        EmailTemplates emailTemplates = this.emailTemplatesRepository.findEmailTemplatesByUuid(uuid);
        if (emailTemplates == null)
            throw new ResourceNotFoundException("Email Template", "uuid", uuid);
        return emailTemplates;
    }

}
