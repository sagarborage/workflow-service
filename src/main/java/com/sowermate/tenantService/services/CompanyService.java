package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.CompanyValue;

import java.util.List;

public interface CompanyService {

    public CompanyValue createCompany(CompanyValue companyValue) throws Exception;

    public CompanyValue editCompany(CompanyValue companyValue) throws Exception;

    public CompanyValue getCompany(String uuid) throws Exception;

    public CompanyValue deleteCompany(String uuid) throws Exception;

    public List<CompanyValue> getAllCompany() throws Exception;
}
