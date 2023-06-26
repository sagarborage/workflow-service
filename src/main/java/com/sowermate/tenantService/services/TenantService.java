package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.TenantValue;

import java.util.List;

public interface TenantService {

 public TenantValue saveTenantDetails(TenantValue tenantValue) throws Exception;

 public List<TenantValue> getAllTenantDetails() throws Exception;
  public TenantValue editTenantDetails(TenantValue tenantValue) throws Exception;

   public TenantValue getTenantDetails(String tenantUuid) throws Exception;

  public TenantValue deleteTenantDetails(String tenantUuid)throws Exception;


}
