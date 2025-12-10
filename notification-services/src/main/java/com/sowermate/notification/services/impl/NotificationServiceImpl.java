package com.sowermate.notification.services.impl;

import com.sowermate.base.common.constants.DropdownConstants;
import com.sowermate.base.common.constants.StatusConstants;
import com.sowermate.base.exceptions.ResourceNotFoundException;
import com.sowermate.notification.dtos.NotificationDto;
import com.sowermate.notification.entities.Notification;
import com.sowermate.notification.projections.NotificationDropdownProjection;
import com.sowermate.notification.repositories.NotificationRepository;
import com.sowermate.notification.repositories.NotificationTypeRepository;
import com.sowermate.notification.services.NotificationService;
import com.sowermate.tenantService.exceptions.InvalidInputException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    /*
     *{@inheritDoc}
     */
    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        Long notificationTypeId=getNotificationTypeId(notificationDto.getNotificationTypeUuid());
        Notification notification = this.modelMapper.map(notificationDto, Notification.class);
        notification.setNotificationTypeId(notificationTypeId);
        this.notificationRepository.save(notification);
        this.modelMapper.map(notification, notificationDto);
        return notificationDto;

    }
    public Long getNotificationTypeId(String notificationTypeUuid) {
        return this.notificationTypeRepository.findIdByUuid(notificationTypeUuid)
                .orElseThrow(()-> new ResourceNotFoundException("NotificationType","notificationTypeUuid",notificationTypeUuid));

    }
    public Notification getNotification(String uuid, Long notificationTypeId) {
        Notification notification = this.notificationRepository.findNotificationByUuidAndNotificationTypeId(uuid, notificationTypeId);
        if (notification == null)
            throw new ResourceNotFoundException("Notification", "notificationUuid", uuid);
        return notification;

    }
    /*
     *{@inheritDoc}
     */
    @Override
    public NotificationDto updateNotification(NotificationDto notificationDto) {
        Long notificationTypeId=getNotificationTypeId(notificationDto.getNotificationTypeUuid());
        Notification notification = getNotification(notificationDto.getUuid(), notificationTypeId);
        if (notification == null)
            throw new ResourceNotFoundException("Notification", "notificationUuid", notification.getUuid());
        if (notificationDto.getSubject() != null)
            notification.setSubject(notificationDto.getSubject());
        if(notificationDto.getMessage()!=null)
            notification.setMessage(notificationDto.getMessage());
        this.notificationRepository.save(notification);
        this.modelMapper.map(notification,notificationDto);
        return notificationDto;

    }
    /*
     *{@inheritDoc}
     */
    @Override
    public NotificationDto getNotificationByUuid(String notificationUuid, String notificationTypeUuid) {
        Long notificationTypeId=getNotificationTypeId(notificationTypeUuid);
        Notification notification = getNotification(notificationUuid, notificationTypeId);
        NotificationDto notificationDto = this.modelMapper.map(notification, NotificationDto.class);
        notificationDto.setNotificationTypeUuid(notificationTypeUuid);
        return notificationDto;
    }
    /*
     *{@inheritDoc}
     */
    @Override
    public List<NotificationDto> getNotifications(String notificationTypeUuid, String status) {
        List<Notification>  notifications;
        Long notificationTypeId = getNotificationTypeId(notificationTypeUuid);
        if (StatusConstants.ALL.equals(status)) {
            notifications = this.notificationRepository.findAllByNotificationTypeId(notificationTypeId);
        } else if (StatusConstants.ACTIVE.equals(status.toLowerCase())) {
            notifications = this.notificationRepository.findAllByNotificationTypeIdAndIsActive(notificationTypeId, true);
        } else if (StatusConstants.INACTIVE.equals(status.toLowerCase())) {
            notifications = this.notificationRepository.findAllByNotificationTypeIdAndIsActive(notificationTypeId, false);
        } else {
            throw new InvalidInputException("Status is Incorrect");
        }
        List<NotificationDto> notificationDtos = notifications.stream().map(notification -> {
            NotificationDto notificationDto = this.modelMapper.map(notification, NotificationDto.class);
            notificationDto.setNotificationTypeUuid(notificationTypeUuid);
            return notificationDto;
        }).collect(Collectors.toList());
        return notificationDtos;
    }
    /*
     *{@inheritDoc}
     */
    @Override
    public List<Map<String, String>> getNotificationDropdown(String notificationTypeUuid, String status) {
        List<NotificationDropdownProjection>  notificationDropdownProjections;
        Long notificationTypeId = getNotificationTypeId(notificationTypeUuid);
        if (StatusConstants.ALL.equals(status)) {
            notificationDropdownProjections = this.notificationRepository.findByNotificationTypeId(notificationTypeId);
        } else if (StatusConstants.ACTIVE.equals(status.toLowerCase())) {
            notificationDropdownProjections = this.notificationRepository.findByNotificationTypeIdAndIsActive(notificationTypeId, true);
        } else if (StatusConstants.INACTIVE.equals(status.toLowerCase())) {
            notificationDropdownProjections = this.notificationRepository.findByNotificationTypeIdAndIsActive(notificationTypeId, false);
        } else {
            throw new InvalidInputException("Status is Incorrect");
        }
        List<Map<String, String>> notificationDropdownList = notificationDropdownProjections.stream().map(projection -> {
            Map<String, String> dropdown = new HashMap<>();
            dropdown.put(DropdownConstants.KEY, projection.getUuid());
            dropdown.put(DropdownConstants.VALUE, projection.getSubject());
            return dropdown;
        }).collect(Collectors.toList());

        return notificationDropdownList;
    }
    /*
     *{@inheritDoc}
     */
    @Override
    public void softDeleteNotification(String notificationUuid, String notificationTypeUuid) {
        Long notificationTypeId=getNotificationTypeId(notificationTypeUuid);
        Notification notification=getNotification(notificationUuid,notificationTypeId);
        notification.setIsActive(false);
        this.notificationRepository.save(notification);
    }
    /*
     *{@inheritDoc}
     */
    @Override
    public void hardDeleteNotification(String notificationUuid, String notificationTypeUuid) {
        Long notificationTypeId=getNotificationTypeId(notificationTypeUuid);
        Notification notification=getNotification(notificationUuid,notificationTypeId);
        this.notificationRepository.delete(notification);

    }
    /*
     *{@inheritDoc}
     */
    @Override
    public Long getNotificationId(String notificationUuid) {
        return this.notificationRepository.findIdByUuid(notificationUuid)
                .orElseThrow(()-> new ResourceNotFoundException("Notification","notificationUuid",notificationUuid));
    }
}
