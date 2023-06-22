package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class GlassThicknessValue {

    private String tenantUuid;
    private String glassThicknessUuid;
    private String name;
    private Boolean isActive;
}
