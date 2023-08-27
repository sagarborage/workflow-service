package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.CompanyTypeEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyTypeValue {
    private Integer companyTypeId;
    private String type;
    private String description;
    protected String companyTypeUuid;
    private Boolean isActive;

    private CompanyValue companyValue;
    private TenantValue tenantValue;


    public CompanyTypeEntity toEntity() {
        return CompanyTypeEntity.newBuilder()
                .companyTypeId(getCompanyTypeId())
                .type(getType())
                .description(getDescription())
                .companyTypeUuid(getCompanyTypeUuid())
                .isActive(getIsActive())
                .build();
    }
}
