package com.sowermate.base.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public abstract class Base extends BaseId {
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_datetime")
    private LocalDateTime createdDateTime;
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDateTime;

    @Column(name = "version")
    @Version
    private Integer version;

    @PrePersist
    public void autofillCreate() {
        if (version == null) {
            version = 1;
        }
        if (createdDateTime == null) {
            createdDateTime = LocalDateTime.now();
        }
        if (lastUpdatedDateTime == null) {
            lastUpdatedDateTime = LocalDateTime.now();
        }

        AuthenticatedUserDetails userDetails = getUserDetails();
        if (null != userDetails) {
            createdBy = userDetails.getFullName();
        } else {
            createdBy = "System";
        }
        lastUpdatedBy = createdBy;
    }

    private AuthenticatedUserDetails getUserDetails() {
        if (null != SecurityContextHolder.getContext()) {
            if (null != SecurityContextHolder.getContext().getAuthentication() && SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken token) {
                if (null != token.getPrincipal()) {
                    return (AuthenticatedUserDetails) token.getPrincipal();
                }
            }
        }
        return null;
    }

    @PreUpdate
    public void autofillUpdate() {
        lastUpdatedDateTime = LocalDateTime.now();
        AuthenticatedUserDetails userDetails = getUserDetails();
        if (null != userDetails) {
            lastUpdatedBy = userDetails.getFullName();
        } else {
            lastUpdatedBy = "System";
        }
    }
}
