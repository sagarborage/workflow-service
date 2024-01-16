package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.TenantValue;

import java.util.List;

public interface TenantService {

 public TenantValue saveTenantDetails(TenantValue tenantValue);

 public List<TenantValue> getAllTenantDetails();
  public TenantValue editTenantDetails(TenantValue tenantValue);

   public TenantValue getTenantDetails(String tenantUuid);

  public TenantValue deleteTenantDetails(String tenantUuid);

 /**
  * Retrieves the ID of a tenant based on its UUID.
  *
  * @param tenantUuid The UUID of the tenant for which the ID is being retrieved.
  * @return The ID of the tenant if found, or throws a ResourceNotFoundException if not found.
  * @throws com.sowermate.base.exceptions.ResourceNotFoundException If the tenant with the specified UUID is not found.
  */
 Long getTenantId(String tenantUuid);
 String getTenantUuid(Long tenantId);

}
