package com.sowermate.user.entities;

import com.sowermate.flexipunch.entities.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "verification_token")
public class VerificationToken extends BaseId {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "token_value")
    private String tokenValue;
    @Column(name = "created_at")
    private LocalDateTime createdAT;
    @Column(name = "expired_at")
    private LocalDateTime expiredAt;
}
