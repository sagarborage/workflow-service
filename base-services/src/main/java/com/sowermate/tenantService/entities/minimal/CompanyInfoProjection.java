package com.sowermate.tenantService.entities.minimal;

import com.sowermate.tenantService.entities.AddressEntity;
import com.sowermate.tenantService.entities.CompanyEntity;

import java.util.List;

public interface CompanyInfoProjection {

    List<AddressEntity> getAddresses();
    String getGstNumber();
    String getPanNumber();
    String getTanNumber();

}
