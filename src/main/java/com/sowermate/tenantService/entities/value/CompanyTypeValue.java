package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class CompanyTypeValue {

    private String type;
    private String description;
    private String uuid;
    private Boolean isActive;
}
