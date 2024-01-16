package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.TenantValue;

import java.util.List;

public interface TenantService {

 public TenantValue saveTenantDetails(TenantValue tenantValue);

 public List<TenantValue> getAllTenantDetails();
  public TenantValue editTenantDetails(TenantValue tenantValue);

   public TenantValue getTenantDetails(String tenantUuid);

  public TenantValue deleteTenantDetails(String tenantUuid);


}
