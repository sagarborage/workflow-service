package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AdditionalChargesEntity;
import com.sowermate.tenantService.entities.AddressEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.AddressRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(rollbackForClassName= {"Exception"})
public class AddressServiceImpl  implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public AddressValue createAddress(AddressValue addressValue) {
        AddressEntity addressEntity = addressValue.toEntity();
        return addressRepository.save(addressEntity).toDTO();
    }

    @Override
    public AddressValue editAddress(AddressValue addressValue) {
        AddressEntity  addressEntity = addressValue.toEntity();

        addressEntity.toBuilder().id(addressRepository.findByUuid(addressValue.getUuid()).getId()).build();
        BeanUtils.copyProperties(addressRepository.save(addressEntity), addressValue);
        return addressRepository.save(addressEntity).toDTO();
    }

    @Override
    public AddressValue getAddress( String tenantUuid, String addressUuid) {
        AddressEntity addressEntity = addressRepository.findByUuid(addressUuid);
        return addressEntity.toDTO();
    }

    @Override
    public AddressValue deleteAddress( String tenantUuid,String addressUuid) {
        addressRepository.softDelete(addressUuid);
        AddressEntity  addressEntity = addressRepository.findByUuid(addressUuid);
        return addressEntity.toDTO();
    }

    @Override
    public List<AddressValue> getAllCompanyAddress(String tenantUuid) {
        List<AddressValue> addressValues = new ArrayList<>();
        return addressValues;
    }

    public TenantEntity getTenantEntity(String tenantUuid){
        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUuid);
        if (tenantEntity==null){
            throw new ResourceNotFoundException("TenantEntity","tenantUuid",tenantUuid);
        }
        return tenantEntity;
    }

    public AddressEntity getAddressEntity(String addressEntityUuid){
        AddressEntity addressEntity = addressRepository.findByUuid(addressEntityUuid);
        if (addressEntity==null){
            throw new ResourceNotFoundException("AdditionalChargesEntity","additionalChargesEntityUuid",addressEntityUuid);
        }
        return addressEntity;
    }
}
