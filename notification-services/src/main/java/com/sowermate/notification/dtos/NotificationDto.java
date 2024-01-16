package com.sowermate.notification.dtos;

import com.sowermate.flexipunch.dtos.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Priti
 * <h1>NotificationDto Class</h1>
 * This Data Transfer Object (DTO) class represents a Notification object for use in API calls.
 * It extends the {@link BaseDto} class which provides common fields such as UUID.
 * @author pgawade
 * @version 1.0
 * @see BaseDto
 * @since 2023-11-24
 */
@Getter
@Setter

public class NotificationDto extends BaseDto {
    /* The notificationType ID to which the notificationType belongs.
     * This is a foreign key linking to the Notification table.
     */
    private String notificationTypeUuid;

    /**
     * Subject of Notification.
     */
    private String subject;

    /**
     * message of Notification.
     */
    private String message;

}
