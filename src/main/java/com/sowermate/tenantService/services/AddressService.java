package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.AddressValue;

import java.util.List;

public interface AddressService {
    public AddressValue createAddress(AddressValue addressValue) throws Exception;

    public AddressValue editAddress(AddressValue addressValue) throws Exception;

    public AddressValue getAddress(String uuid) throws Exception;

    public AddressValue deleteAddress(String uuid) throws Exception;

    public List<AddressValue> getAllCompanyAddress() throws Exception;

}
