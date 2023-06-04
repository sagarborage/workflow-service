package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.TenantDetailsValue;

import java.util.List;

public interface TenantService {

 public TenantDetailsValue SaveTenantDetails(TenantDetailsValue tenantDetailsValue) throws Exception;

 public List<TenantDetailsValue> getAllTenantDetails() throws Exception;
  public TenantDetailsValue editTenantDetails(TenantDetailsValue tenantDetailsValue) throws Exception;

   public TenantDetailsValue getTenantDetails(String uuid) throws Exception;

  public TenantDetailsValue deleteTenantDetails(int tenantId)throws Exception;


}
