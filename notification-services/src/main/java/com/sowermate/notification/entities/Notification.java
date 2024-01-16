package com.sowermate.notification.entities;

import com.sowermate.flexipunch.entities.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** <h1>Notification Class</h1>
 * This class represents a Notification entity in the application. It extends the {@link Base} class
 * which provides common entity fields such as UUID, creation and update timestamps.
 * @author pgawade
 * @version 1.0
 * @see Base
 * @since 2023-11-24
 */
@Getter
@Setter
@Table(name="notifications")
@Entity

public class Notification extends Base {
    /* The notificationType ID to which the notificationType belongs.
     * This is a foreign key linking to the Notification table.
     */
    @Column(name = "notification_type_id")
    private Long notificationTypeId;

    /**
     * Subject of Notification.
     */
    @Column(name = "subject")
    private String subject;

    /**
     * message of Notification.
     */
    @Column(name = "message")
    private String message;


}
