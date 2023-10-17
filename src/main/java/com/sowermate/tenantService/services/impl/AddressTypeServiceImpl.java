package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AddressTypeEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.AddressTypeValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.AddressTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.AddressTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class AddressTypeServiceImpl implements AddressTypeService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    public AddressTypeEntity getAddressTypeEntity(String tenantUuid,String addressTypeUuid){
        AddressTypeEntity addressTypeEntity = addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(tenantUuid,addressTypeUuid);
        if (addressTypeEntity==null){
            throw new ResourceNotFoundException("AddressType","tenantUuid or addressTypeUuid",tenantUuid+" or "+ addressTypeUuid);
        }
        return addressTypeEntity;
    }

    public TenantEntity getTenantEntity(String tenantUuid){
        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUuid);
        if (tenantEntity==null){
            throw new ResourceNotFoundException("TenantEntity","tenantUuid",tenantUuid);
        }
        return tenantEntity;
    }
    @Override
    public AddressTypeValue createAddressType(AddressTypeValue addressTypeValue) {
        AddressTypeEntity addressTypeEntity = addressTypeValue.toEntity().toBuilder()
                .tenantEntity(getTenantEntity(addressTypeValue.getTenantUuid()))
                        .build();
        return addressTypeRepository.save(addressTypeEntity).toDTO();
    }

    @Override
    public AddressTypeValue editAddressType(AddressTypeValue addressTypeValue) {
        AddressTypeEntity addressTypeEntityTemp = getAddressTypeEntity(addressTypeValue.getTenantUuid(),
                addressTypeValue.getUuid());
        AddressTypeEntity addressTypeEntity = addressTypeValue.toEntity().toBuilder()
                .id(addressTypeEntityTemp.getId())
                .tenantEntity(getTenantEntity(addressTypeValue.getTenantUuid()))
                .createdDateTime(addressTypeEntityTemp.getCreatedDateTime())
                .createdBy(addressTypeEntityTemp.getCreatedBy())
                .build();
        return addressTypeRepository.save(addressTypeEntity).toDTO();
    }

    @Override
    public List<AddressTypeValue> getAllAddressType(String tenantUuid) {
        List<AddressTypeEntity> addressTypeEntities = addressTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return addressTypeEntities.stream().map(entity -> entity.toDTO()).collect(Collectors.toList());
    }

    @Override
    public AddressTypeValue getAddressType(String tenantUuid, String addressTypeUuid) {
        return getAddressTypeEntity(tenantUuid, addressTypeUuid).toDTO();
    }

    @Override
    public AddressTypeValue deleteAddressType(String tenantUuid, String addressTypeUuid) {
        addressTypeRepository.softDelete(addressTypeUuid);
        return addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(tenantUuid, addressTypeUuid).toDTO();
    }

}
