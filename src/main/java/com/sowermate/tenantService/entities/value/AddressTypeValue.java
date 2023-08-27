package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.AddressTypeEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressTypeValue {

    private Integer addressTypeId;
    private String type;
    private String description;
    private String addressTypeUuid;
    private Boolean isActive;

    private TenantValue tenantValue;

    private List<CompanyAddressValue> companyAddresses;

    public AddressTypeEntity toEntity() {
        return AddressTypeEntity.newBuilder()
                .addressTypeId(getAddressTypeId())
                .addressTypeUuid(getAddressTypeUuid())
                .type(getType())
                .description(getDescription())
                .isActive(getIsActive())
                .tenantEntity(getTenantValue().toEntity())
                .companyAddresses(getCompanyAddresses().stream().map(a->a.toEntity()).collect(Collectors.toList()))
                .build();
    }
}
