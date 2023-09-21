package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class StatusValue {
    private String statusUuid;
    private String tenantUuid;
    private String name;
    private boolean isActive;
}
