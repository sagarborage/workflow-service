package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class GlassTypeValue {

    private String glassTypeUuid;
    private String tenantUuid;
    private String glassName;
    private Boolean isActive;

}
