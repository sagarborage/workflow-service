package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.CompanyEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyValue extends BaseValue {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long companyId;
    protected String companyUuid;
    private String tenantUuid;
    private String companyTypeUuid;
    private String companyName;
    private String cin;
    private String gstin;
    private String tan;
    private String pan;
    private List<AddressValue> addresses;

    public CompanyEntity toEntity() {
        return CompanyEntity.newBuilder()
                .id(getCompanyId())
                .uuid(getCompanyUuid())
                .companyName(getCompanyName())
                .cin(getCin())
                .gstin(getGstin())
                .tan(getTan())
                .pan(getPan())
                .isActive(getIsActive())
                //.tenantEntity(TenantEntity.newBuilder().uuid(getCompanyUuid()).build())
                //.companyAddresses(Optional.ofNullable(getCompanyAddresses()).map(e->e.stream().map(el->el.toEntity()).collect(Collectors.toList())).orElse(null))
                //.companyType(CompanyTypeEntity.newBuilder().companyTypeUuid(getCompanyTypeUuid()).build())
                .build();
    }
}
