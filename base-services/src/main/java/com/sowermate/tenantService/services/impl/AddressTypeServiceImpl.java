package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AddressTypeEntity;
import com.sowermate.tenantService.entities.value.AddressTypeValue;
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

    @Override
    public AddressTypeValue createAddressType(AddressTypeValue addressTypeValue) {
        AddressTypeEntity addressTypeEntity = addressTypeValue.toEntity().toBuilder()
                .tenantEntity(tenantRepository.findByUuid(addressTypeValue.getTenantUuid()))
                        .build();
        return addressTypeRepository.save(addressTypeEntity).toDTO();
    }

    @Override
    public AddressTypeValue editAddressType(AddressTypeValue addressTypeValue) {
        AddressTypeEntity addressTypeEntityTemp = addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(addressTypeValue.getAddressTypeUuid(),
                addressTypeValue.getAddressTypeUuid());
        AddressTypeEntity addressTypeEntity = addressTypeValue.toEntity().toBuilder()
                .id(addressTypeEntityTemp.getId())
                .tenantEntity(tenantRepository.findByUuid(addressTypeValue.getTenantUuid()))
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
        return addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(tenantUuid, addressTypeUuid).toDTO();
    }

    @Override
    public AddressTypeValue deleteAddressType(String tenantUuid, String addressTypeUuid) {
        addressTypeRepository.softDelete(addressTypeUuid);
        return addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(tenantUuid, addressTypeUuid).toDTO();
    }
}
