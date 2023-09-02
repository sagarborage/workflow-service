package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.CompanyAddressEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyAddressValue {
    @JsonIgnore
    private Integer companyAddressId;
    private String companyAddressUuid;
    private String addressTypeUuid;
    private Boolean isActive;

    private String companyUuid;
    private AddressValue addressValue;
    //private AddressTypeValue addressTypeValue;

    public CompanyAddressEntity toEntity() {
        return CompanyAddressEntity.newBuilder()
                .companyAddressId(getCompanyAddressId())
                .companyAddressUuid(getCompanyAddressUuid())
                .isActive(getIsActive())
                //.companyEntity(null != getCompanyValue() ? getCompanyValue().toEntity() : null)
                .addressEntity(null != getAddressValue() ? getAddressValue().toEntity() : null)
                //.addressTypeEntity(null != getAddressTypeValue() ? getAddressTypeValue().toEntity() : null)
                .build();
    }
}
