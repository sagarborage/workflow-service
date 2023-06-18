package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class ServiceRateValue {
    private String uuid;
    private String name;
    private float rate;
    private Boolean isActive;
    private String tenantUUID;
}
