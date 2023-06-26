package com.sowermate.tenantService.entities.value;

import lombok.Data;

import java.util.Date;

@Data
public class CompanyValue {
    private String companyUuid;
    private String companyName;
    private int cin;
    private int gstin;
    private int tan;
    private String pan;
    protected Date createdDttm;
    protected Date updatedDttm;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
    private String tenantUuid;
    private String companyTypeUuid;

}
