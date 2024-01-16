package com.sowermate.user.entities;

import com.sowermate.flexipunch.entities.Base;
import com.sowermate.flexipunch.entities.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <h1>ConfirmationCode Class</h1>
 * This ConfirmationCode represents a ConfirmationCode entity in the application. It extends the {@link Base} class
 * which provides common entity fields such as UUID, creation and update timestamps.
 *
 * @author ajadhav
 * @version 1.0
 * @see Base
 * @since 2023-12-15
 */
@Getter
@Setter
@Entity
@Table(name = "confirmation_code")
public class ConfirmationCode extends BaseId {
    /**
     * The userId of the ConfirmationCode.
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * The code of the ConfirmationCode.
     */
    @Column(name = "code")
    private String code;

    /**
     * The operation of the ConfirmationCode.
     */
    @Column(name = "operation")
    private String operation;

    /**
     * The expiresAt of the ConfirmationCode.
     */
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    /**
     * The createdAt of the ConfirmationCode.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * The resetAttempts of the ConfirmationCode.
     */
    @Column(name = "reset_attempts")
    private Long resetAttempts;

    /**
     * The isUsed of the ConfirmationCode.
     */
    @Column(name = "is_used")
    private Boolean isUsed;

}
