package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class CompanyValue extends  CommonValue {
    private String uuid;
    private String companyName;
    private int cin;
    private int gstin;
    private int tan;
    private String pan;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
    private String tenantUUID;
    private String addressUUID;
    private String companyTypeUUID;

}
