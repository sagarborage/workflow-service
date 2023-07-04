package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class PiTypeValue {

    private String  piTypeUuid;
    private String tenantUuid;
    private float mm;
    private float sqft;
}
