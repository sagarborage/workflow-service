package com.sowermate.tenantService.entities.value;

import com.sowermate.tenantService.entities.common.Base;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class BaseValue {
    private Long id;
    private String uuid;
    private LocalDateTime createdDttm;
    private LocalDateTime updatedDttm;
    private String createdBy;
    private String updatedBy;
    private boolean isActive;
}
