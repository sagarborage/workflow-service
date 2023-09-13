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
public class CompanyTypeValue extends BaseValue {
    private Long companyTypeId;
    private String type;
    private String description;
    protected String companyTypeUuid;
    private Boolean isActive;

    private CompanyValue companyValue;
    private TenantValue tenantValue;


    public CompanyTypeEntity toEntity() {
        return CompanyTypeEntity.newBuilder()
                .id(getCompanyTypeId())
                .uuid(getCompanyTypeUuid())
                .type(getType())
                .description(getDescription())
                .createdDatetime(getCreatedDttm())
                .lastUpdatedDatetime(getUpdatedDttm())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
