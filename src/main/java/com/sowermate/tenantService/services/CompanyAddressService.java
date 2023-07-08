package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.CompanyAddressValue;

import java.util.List;

public interface CompanyAddressService {
    public CompanyAddressValue createCompanyAddress(CompanyAddressValue companyAddressValue) throws  Exception;

    public  CompanyAddressValue editCompanyAddress(CompanyAddressValue companyAddressValue) throws  Exception;

    public  CompanyAddressValue getCompanyAddress(String tenantUuid, String companyAddressUuid) throws  Exception;

    public List<CompanyAddressValue> getAllCompanyAddress(String tenantUuid) throws  Exception;

    public  int deleteCompanyAddress(String tenantUuid ,String companyAddressUuid) throws  Exception;
}
