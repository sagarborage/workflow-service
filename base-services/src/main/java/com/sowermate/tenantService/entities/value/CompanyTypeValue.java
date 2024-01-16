package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.CompanyTypeEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyTypeValue extends BaseValue {
    private Long companyTypeId;
    private String type;
    private String description;
    protected String companyTypeUuid;

    private CompanyValue companyValue;
    private TenantValue tenantValue;


    public CompanyTypeEntity toEntity() {
        return CompanyTypeEntity.newBuilder()
                .id(getCompanyTypeId())
                .uuid(getCompanyTypeUuid())
                .type(getType())
                .description(getDescription())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
