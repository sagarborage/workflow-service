package com.sowermate.notification.services.impl;

import com.sowermate.base.common.constants.DropdownConstants;
import com.sowermate.base.common.constants.StatusConstants;
import com.sowermate.base.exceptions.ResourceNotFoundException;
import com.sowermate.notification.dtos.NotificationTypeDto;
import com.sowermate.notification.entities.NotificationType;
import com.sowermate.notification.projections.NotificationTypeDropdownProjection;
import com.sowermate.notification.repositories.NotificationTypeRepository;
import com.sowermate.notification.services.NotificationTypeService;
import com.sowermate.tenantService.exceptions.InvalidInputException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationTypeServiceImpl implements NotificationTypeService {

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    /*
     *{@inheritDoc}
     */
    @Override
    public NotificationTypeDto createNotificationType(NotificationTypeDto notificationTypeDto) {
        NotificationType notificationType=this.modelMapper.map(notificationTypeDto, NotificationType.class);
        this.notificationTypeRepository.save(notificationType);
        this.modelMapper.map(notificationType,notificationTypeDto);
        return notificationTypeDto;
    }
    public NotificationType getNotificationType(String uuid)
    {
        NotificationType notificationType=this.notificationTypeRepository.findNotificationTypeByUuid(uuid);
        if(notificationType==null)
            throw new ResourceNotFoundException("NotificationType","NotificationTypeUuid",uuid);
        return notificationType;
    }

    /*
     *{@inheritDoc}
     */
    @Override
    public NotificationTypeDto updateNotificationType(NotificationTypeDto notificationTypeDto) {
        NotificationType notificationType=getNotificationType(notificationTypeDto.getUuid());
        if(notificationType==null)
            throw new ResourceNotFoundException("NotificationType","NotificationTypeUuid",notificationTypeDto.getUuid());
        if(notificationTypeDto.getTypeName()!=null)
            notificationType.setTypeName(notificationTypeDto.getTypeName());
        if(notificationTypeDto.getDescription()!=null)
            notificationType.setDescription(notificationTypeDto.getDescription());
        if(notificationTypeDto.getIconUrl()!=null)
            notificationType.setIconUrl(notificationTypeDto.getIconUrl());
        this.notificationTypeRepository.save(notificationType);
        this.modelMapper.map(notificationType,notificationTypeDto);
        return notificationTypeDto;
    }

    /*
     *{@inheritDoc}
     */
    @Override
    public NotificationTypeDto getNotificationTypeByUuid(String notificationTypeUuid) {
        NotificationType notificationType=getNotificationType(notificationTypeUuid);
        NotificationTypeDto notificationTypeDto=this.modelMapper.map(notificationType, NotificationTypeDto.class);
        return notificationTypeDto;
    }

    /*
     *{@inheritDoc}
     */
    @Override
    public List<NotificationTypeDto> getNotificationTypes(String status) {
        List<NotificationType>  notificationTypes;
        if (StatusConstants.ALL.equals(status)) {
            notificationTypes = this.notificationTypeRepository.findAll();
        } else if (StatusConstants.ACTIVE.equals(status.toLowerCase())) {
            notificationTypes = this.notificationTypeRepository.findAllNotificationTypesByIsActive(true);
        } else if (StatusConstants.INACTIVE.equals(status.toLowerCase())) {
            notificationTypes = this.notificationTypeRepository.findAllNotificationTypesByIsActive(false);
        } else {
            throw new InvalidInputException("Status is Incorrect");

        }
        List<NotificationTypeDto> notificationTypeDtoList = notificationTypes.stream().map(notificationType -> {
            NotificationTypeDto notificationTypeDto = this.modelMapper.map(notificationType, NotificationTypeDto.class);
            return notificationTypeDto;
        }).collect(Collectors.toList());
        return notificationTypeDtoList;
    }

    /*
     *{@inheritDoc}
     */
    @Override
    public List<Map<String, String>> getNotificationTypeDropdown(String status) {
        List<NotificationTypeDropdownProjection> notificationTypeDropdownProjections;
        if (StatusConstants.ALL.equals(status)) {
            notificationTypeDropdownProjections = this.notificationTypeRepository.findAllBy();
        } else if (StatusConstants.ACTIVE.equals(status.toLowerCase())) {
            notificationTypeDropdownProjections = this.notificationTypeRepository.findNotificationTypesByIsActive(true);
        } else if (StatusConstants.INACTIVE.equals(status.toLowerCase())) {
            notificationTypeDropdownProjections = this.notificationTypeRepository.findNotificationTypesByIsActive(false);
        } else {
            throw new InvalidInputException("Status is Incorrect");
        }
        List<Map<String, String>> notificationTypeDropdownList = notificationTypeDropdownProjections.stream().map(projection -> {
            Map<String, String> dropdown = new HashMap<>();
            dropdown.put(DropdownConstants.KEY, projection.getUuid());
            dropdown.put(DropdownConstants.VALUE, projection.getTypeName());
            return dropdown;
        }).collect(Collectors.toList());
        return notificationTypeDropdownList;
    }


    /*
     *{@inheritDoc}
     */
    @Override
    public void softDeleteNotificationType(String notificationTypeUuid) {
        NotificationType notificationType=getNotificationType(notificationTypeUuid);
        notificationType.setIsActive(false);
        this.notificationTypeRepository.save(notificationType);

    }

    /*
     *{@inheritDoc}
     */
    @Override
    public void hardDeleteNotificationType(String notificationTypeUuid) {
        NotificationType notificationType=getNotificationType(notificationTypeUuid);
        this.notificationTypeRepository.delete(notificationType);

    }

    /*
     *{@inheritDoc}
     */
    @Override
    public Long getNotificationTypeId(String notificationTypeUuid) {
        return this.notificationTypeRepository.findIdByUuid(notificationTypeUuid)
                .orElseThrow(()->new ResourceNotFoundException("NotificationType","NotificationTypeUuid",notificationTypeUuid));
    }
}
