package com.sowermate.tenantService.entities.value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public abstract class BaseValue {
    private Long id;
    private String uuid;
    private LocalDateTime createdDateTime;
    private LocalDateTime lastUpdatedDateTime;
    private String createdBy;
    private String lastUpdatedBy;
    private Boolean isActive;
}
