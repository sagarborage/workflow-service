package com.sowermate.base.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;


@Getter
@Setter
@MappedSuperclass
public abstract class Base extends BaseId {
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    @Column(name = "last_updated_datetime")
    private Date lastUpdatedDatetime;

    @Column(name = "version")
    @Version
    private Integer version;

    @PrePersist
    public void autofillCreate() {
        if (version == null) {
            version = 1;
        }
        if (createdDatetime == null) {
            createdDatetime = new Date();
        }
        if (lastUpdatedDatetime == null) {
            lastUpdatedDatetime = new Date();
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
        lastUpdatedDatetime = new Date();
        AuthenticatedUserDetails userDetails = getUserDetails();
        if (null != userDetails) {
            lastUpdatedBy = userDetails.getFullName();
        } else {
            lastUpdatedBy = "System";
        }
    }
}
