package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class CompanyTypeValue {
    protected String companyTypeUuid;
    private String tenantUuid;
    private String type;
    private String description;
    private Boolean isActive;
}
