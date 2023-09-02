package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AddressTypeEntity;
import com.sowermate.tenantService.entities.value.AddressTypeValue;
import com.sowermate.tenantService.repositories.AddressTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.AddressTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class AddressTypeServiceImpl implements AddressTypeService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @Override
    public AddressTypeValue createAddressType(AddressTypeValue addressTypeValue) throws Exception {
        AddressTypeEntity addressTypeEntity = addressTypeValue.toEntity().toBuilder()
                .addressTypeUuid(CommonUtils.generateUUID())
                .tenantEntity(tenantRepository.findByTenantUuid(addressTypeValue.getTenantUuid()))
                        .build();

        //addressTypeEntity.setAddressTypeUuid(CommonUtils.generateUUID());
        //addressTypeEntity.setTenantEntity(tenantRepository.findByTenantUuid(addressTypeValue.getTenantUuid()));
        //BeanUtils.copyProperties(addressTypeRepository.save(addressTypeEntity), addressTypeValue);
        return addressTypeRepository.save(addressTypeEntity).toDTO();
    }

    @Override
    public AddressTypeValue editAddressType(AddressTypeValue addressTypeValue) throws Exception {
        AddressTypeEntity addressTypeEntity = addressTypeValue.toEntity().toBuilder()
                .addressTypeId(addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(addressTypeValue.getAddressTypeUuid(),
                        addressTypeValue.getAddressTypeUuid()).getAddressTypeId())
                .tenantEntity(tenantRepository.findByTenantUuid(addressTypeValue.getTenantUuid()))
                .build();
        //BeanUtils.copyProperties(addressTypeValue, addressTypeEntity);

        // Check that UUID is not null before searching for the tenant
/*        if (addressTypeValue.getAddressTypeUuid() != null) {
            AddressTypeEntity matchingAddressTypes = addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(addressTypeValue.getTenantUuid(), addressTypeValue.getAddressTypeUuid());
            if (matchingAddressTypes != null) {
                addressTypeEntity.setAddressTypeId(matchingAddressTypes.getAddressTypeId());
                addressTypeEntity.setTenantEntity(tenantRepository.findByTenantUuid(addressTypeValue.getTenantUuid()));
                BeanUtils.copyProperties(addressTypeRepository.save(addressTypeEntity), addressTypeValue);
            } else {
                throw new Exception("No tenant found with UUID " + addressTypeValue.getAddressTypeUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }*/

        return addressTypeRepository.save(addressTypeEntity).toDTO();
    }

    @Override
    public List<AddressTypeValue> getAllAddressType(String tenantUuid) throws Exception {
        //List<AddressTypeValue> addressTypeValues = new ArrayList<>();
        //AddressTypeValue addressTypeValue = null;
        List<AddressTypeEntity> addressTypeEntities = addressTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
/*
        for (int i = 0; i < addressTypeEntities.size(); i++) {
            addressTypeValue = new AddressTypeValue();
            BeanUtils.copyProperties(addressTypeEntities.get(i), addressTypeValue);
            addressTypeValue.setTenantUuid(tenantUuid);
            addressTypeValues.add(addressTypeValue);
        }*/

        return addressTypeEntities.stream().map(entity -> entity.toDTO()).collect(Collectors.toList());
    }

    @Override
    public AddressTypeValue getAddressType(String tenantUuid, String addressTypeUuid) throws Exception {
/*        AddressTypeValue addressTypeValue = new AddressTypeValue();

        AddressTypeEntity addressTypeEntity = addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(tenantUuid, addressTypeUuid);
        BeanUtils.copyProperties(addressTypeEntity, addressTypeValue);
        addressTypeValue.setTenantUuid(tenantUuid);*/
        return addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(tenantUuid, addressTypeUuid).toDTO();
    }

    @Override
    public AddressTypeValue deleteAddressType(String tenantUuid, String addressTypeUuid) throws Exception {
        //AddressTypeValue addressTypeValue = new AddressTypeValue();
        addressTypeRepository.softDelete(addressTypeUuid);
        //AddressTypeEntity addressTypeEntity = addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(tenantUuid, addressTypeUuid);
        //BeanUtils.copyProperties(addressTypeEntity, addressTypeValue);
        return addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(tenantUuid, addressTypeUuid).toDTO();
    }
}
