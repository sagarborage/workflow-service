package com.sowermate.notification.entities;


import com.sowermate.flexipunch.entities.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** <h1>NotificationType Class</h1>
 * This class represents a NotificationType entity in the application. It extends the {@link Base} class
 * which provides common entity fields such as UUID, creation and update timestamps.
 *
 * @author pgawade
 * @version 1.0
 * @see Base
 * @since 2023-11-24
 */
@Getter
@Setter
@Table(name = "notification_types")
@Entity

public class NotificationType extends Base {
    /**
     * Name of notification Type.
     */
    @Column(name = "type_name")
    private String typeName;

    /**
     * Description of notification Type.
     */
    @Column(name = "description")
    private String description;

    /**
     * URL of Notification Icon.
     */
    @Column(name = "icon_url")
    private String iconUrl;

}
