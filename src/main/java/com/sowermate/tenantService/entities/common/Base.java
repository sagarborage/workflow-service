package com.sowermate.tenantService.entities.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private String uuid;

    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_dttm")
    private LocalDateTime createdDateTime;
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    @Column(name = "last_updated_dttm")
    private LocalDateTime lastUpdatedDateTime;
    @Column(name = "is_active")
    private Boolean isActive;

    @PrePersist
    public void autofillCreate() {
        if(uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        if(createdDateTime == null) {
            createdDateTime = LocalDateTime.now();
        }
        if(lastUpdatedDateTime == null) {
            lastUpdatedDateTime = LocalDateTime.now();
        }
        if(createdBy == null) {
            createdBy = "ADMIN";
        }
        if(lastUpdatedBy == null) {
            lastUpdatedBy = "ADMIN";
        }
    }

    @PreUpdate
    public void autofillUpdate() {
        lastUpdatedDateTime = LocalDateTime.now();
        lastUpdatedBy = "ADMIN";
    }
}
