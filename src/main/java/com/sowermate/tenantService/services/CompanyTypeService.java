package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.CompanyTypeValue;

public interface CompanyTypeService {
    public CompanyTypeValue createCompanyType(CompanyTypeValue companyTypeValue) throws Exception;

    public CompanyTypeValue editCompanyType(CompanyTypeValue companyTypeValue) throws Exception;

    //public CompanyTypeValue getAllCompanyType(String tenantUuid) throws Exception;

    public CompanyTypeValue getCompanyType(String tenantUuid,String companyTypeUuid) throws Exception;

    public CompanyTypeValue deleteCompanyType(String tenantUuid,String companyTypeUuid)throws Exception;
}
