package com.sowermate.base.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class BaseDto {

    private String uuid;
    private Boolean isActive;
    private String createdBy;
    private LocalDateTime createdDatetime;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedDatetime;
    private Integer version;
}
