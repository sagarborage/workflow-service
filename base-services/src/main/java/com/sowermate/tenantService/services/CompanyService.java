package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.minimal.CompanyInfoProjection;
import com.sowermate.tenantService.entities.value.CompanyValue;

import java.util.List;

public interface CompanyService {

    public CompanyValue createCompany(CompanyValue companyValue);

    public CompanyValue editCompany(CompanyValue companyValue);

    public CompanyValue getCompany(String tenantUuid,String companyUuid);

    public CompanyValue deleteCompany(String tenantUuid,String companyUuid);

    public List<CompanyValue> getAllCompany(String tenantUuid );

    public CompanyInfoProjection getCompanyInfo(String companyUuid, String tenantUuid);
}
