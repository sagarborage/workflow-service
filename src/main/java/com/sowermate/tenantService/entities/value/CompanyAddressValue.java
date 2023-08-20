package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyAddressValue {
    private String companyAddressUuid;
    private String addressTypeUuid;
    private String tenantUuid;
    private String companyUuid;
    private String addressUuid;
    private Boolean isActive;

    private AddressValue address;
}
