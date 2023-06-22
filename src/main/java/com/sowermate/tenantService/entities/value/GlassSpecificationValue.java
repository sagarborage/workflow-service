package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class GlassSpecificationValue {

    private String tenantUuid;
    private String glassSpecificationUuid;
    private String name;
    private Boolean isActive;
}
