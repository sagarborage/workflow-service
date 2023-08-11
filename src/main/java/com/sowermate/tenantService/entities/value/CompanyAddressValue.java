package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyAddressValue {
    private String companyAddressUuid;
    private String tenantUuid;
    private String companyUuid;
    private String addressUuid;
    private String  addressTypeUuid;
    private Boolean isActive;

    private AddressValue address;
}
