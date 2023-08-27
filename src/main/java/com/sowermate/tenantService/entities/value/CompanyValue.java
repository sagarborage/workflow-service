package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.CompanyAddressEntity;
import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.CompanyTypeEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyValue {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;
    protected String companyUuid;
    private String companyName;
    private String cin;
    private String gstin;
    private String tan;
    private String pan;
    private Boolean isActive;
    private TenantValue tenantValue;
    private CompanyTypeValue companyTypeValue;
    private List<CompanyAddressValue> companyAddresses;
    protected Date createdDttm;
    protected Date updatedDttm;
    private String createdBy;
    private String updatedBy;

    public CompanyEntity toEntity() {
        return CompanyEntity.newBuilder()
                .companyId(getCompanyId())
                .companyUuid(getCompanyUuid())
                .companyName(getCompanyName())
                .cin(getCin())
                .gstin(getGstin())
                .tan(getTan())
                .pan(getPan())
                .isActive(getIsActive())
                //.tenantEntity(TenantEntity.fromTenantValue(getTenantEntity()))
                .tenantEntity(getTenantValue().toEntity())
                //.companyTypeEntity(CompanyTypeEntity.fromCompanyTypeValue(getCompanyTypeEntity()))
                .companyTypeEntity(getCompanyTypeValue().toEntity())
                .build();
    }
}
