package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class CompanyAddressValue {
    private String companyAddressUuid;
    private String tenantUuid;
    private String companyUuid;
    private String addressUuid;
    private String  addressTypeUuid;
    private Boolean isActive;
}
