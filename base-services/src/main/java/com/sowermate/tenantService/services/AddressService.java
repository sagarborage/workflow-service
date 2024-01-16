package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.AddressValue;

import java.util.List;

public interface AddressService {
    public AddressValue createAddress(AddressValue addressValue);

    public AddressValue editAddress(AddressValue addressValue);

    public AddressValue getAddress(String tenantUuid,String addressUuid);

    public AddressValue deleteAddress(String tenantUuid,String addressUuid);

    public List<AddressValue> getAllCompanyAddress(String tenantUuid);

}
