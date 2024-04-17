package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.CompanyTypeValue;

import java.util.List;

public interface CompanyTypeService {
    public CompanyTypeValue createCompanyType(CompanyTypeValue companyTypeValue);

    public CompanyTypeValue editCompanyType(CompanyTypeValue companyTypeValue);

    public List<CompanyTypeValue> getAllCompanyType(String tenantUuid);

    public CompanyTypeValue getCompanyType(String tenantUuid,String companyTypeUuid);

    public CompanyTypeValue deleteCompanyType(String tenantUuid,String companyTypeUuid);
}
