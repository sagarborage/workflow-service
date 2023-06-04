package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class StatusValue {
    private int glassThicknessId;
    private String uuid;
    private String name;
    private Boolean isActive;
}
