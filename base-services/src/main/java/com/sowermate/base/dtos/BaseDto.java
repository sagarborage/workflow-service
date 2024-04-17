package com.sowermate.base.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class BaseDto extends BaseIdDto {

    private Boolean isActive;
    private String createdBy;
    private LocalDateTime createdDateTime;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedDateTime;
    private Integer version;
}
