package com.sowermate.notification.dtos;


import com.sowermate.base.common.constants.ValidationConstants;
import com.sowermate.base.dtos.BaseDto;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>NotificationTypeDto Class</h1>
 * This Data Transfer Object (DTO) class represents a NotificationType object for use in API calls.
 * It extends the {@link BaseDto} class which provides common fields such as UUID.
 * @author pgawade
 * @version 1.0
 * @see BaseDto
 * @since 2023-11-24
 */

@Getter
@Setter

public class NotificationTypeDto extends BaseDto {
    /**
     * Name of notification Type.
     */
    @Size(min = 2, max = 50, message = "{" + ValidationConstants.VALIDATION_SIZE_MIN_MAX + "}")
    private String typeName;

    /**
     * Description of notification Type.
     */
    private String description;

    /**
     * URL of Notification Icon.
     */
    private String iconUrl;

}
