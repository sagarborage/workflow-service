package com.sowermate.workflow.domain.entities.minimal;

import com.sowermate.core.tenant.entities.Address;

import java.util.List;

public interface CompanyInfoProjection {

    List<Address> getAddresses();

    String getGstNumber();

    String getPanNumber();

    String getTanNumber();

}
