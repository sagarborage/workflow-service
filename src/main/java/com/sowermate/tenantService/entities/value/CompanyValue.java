package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyValue {
    private String companyUuid;
    private String companyName;
    private String cin;
    private String gstin;
    private String tan;
    private List<CompanyAddressValue> companyAddresses;

    private String pan;
    protected Date createdDttm;
    protected Date updatedDttm;
    private String createdBy;
    private String updatedBy;
    private Boolean isActive;
    private String tenantUuid;
    private String companyTypeUuid;

}
