package com.sowermate.workflow.domain.entities.value;

import lombok.Data;

@Data
public class StatusValue {
    private String uuid;
    private String tenantUuid;
    private String name;
    private Boolean isActive;
}
