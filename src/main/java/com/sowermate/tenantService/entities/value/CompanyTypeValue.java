package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class CompanyTypeValue {

    private Integer companyTypeId;
    private String type;
    private String description;
    private String uuid;
    private Boolean isActive;
}
