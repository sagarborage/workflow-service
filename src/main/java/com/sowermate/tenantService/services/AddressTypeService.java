package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.AddressTypeValue;

import java.util.List;

public interface AddressTypeService {
    public AddressTypeValue createAddressType(AddressTypeValue addressTypeValue);

    public AddressTypeValue editAddressType(AddressTypeValue addressTypeValue);

    public List<AddressTypeValue> getAllAddressType(String tenantUuid);


    public AddressTypeValue getAddressType(String tenantUuid,String addressTypeUuid);

    public AddressTypeValue deleteAddressType(String tenantUuid,String addressTypeUuid);

}
