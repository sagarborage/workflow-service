package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.AddressTypeValue;
import com.sowermate.tenantService.entities.value.ServiceRateValue;

import java.util.List;

public interface AddressTypeService {
    public AddressTypeValue createAddressType(AddressTypeValue addressTypeValue) throws Exception;

    public AddressTypeValue editAddressType(AddressTypeValue addressTypeValue) throws Exception;

    public List<AddressTypeValue> getAllAddressType(String tenantUuid) throws Exception;


    public AddressTypeValue getAddressType(String tenantUuid,String addressTypeUuid) throws Exception;

    public AddressTypeValue deleteAddressType(String tenantUuid,String addressTypeUuid)throws Exception;

}
