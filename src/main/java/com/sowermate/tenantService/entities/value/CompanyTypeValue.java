package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class CompanyTypeValue {
    private String companyTypeUuid;
    private String type;
    private String description;
    private Boolean isActive;
}
