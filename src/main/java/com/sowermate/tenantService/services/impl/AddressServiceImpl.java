package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AddressEntity;
import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.repositories.AddressRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Transactional(rollbackForClassName= {"Exception"})
public class AddressServiceImpl  implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public AddressValue createAddress(AddressValue addressValue) throws Exception {
        AddressEntity addressEntity=new AddressEntity();
        BeanUtils.copyProperties(addressValue ,addressEntity);
        String randomAddressUuid= UUID.randomUUID().toString();
        addressEntity.setAddressUuid(randomAddressUuid);
        addressEntity.setTenantEntity(tenantRepository.findByTenantUuid(addressValue.getTenantUuid()));
        BeanUtils.copyProperties(addressRepository.save(addressEntity), addressValue);
        return addressValue;
    }

    @Override
    public AddressValue editAddress(AddressValue addressValue) throws Exception {
        AddressEntity  addressEntity=new AddressEntity();
        BeanUtils.copyProperties(addressValue , addressEntity);
        addressEntity.setAddressId(addressRepository.findByTenantEntity_UuidAndAddressUuid( addressValue.getTenantUuid(),addressValue.getAddressUuid()).getAddressId());
        addressEntity.setTenantEntity(tenantRepository.findByTenantUuid(addressValue.getTenantUuid()));
        BeanUtils.copyProperties(addressRepository.save(addressEntity), addressValue);
        return addressValue;
    }

    @Override
    public AddressValue getAddress( String tenantUuid,String addressUuid) throws Exception {
        AddressValue addressValue =new AddressValue();
        AddressEntity addressEntity=addressRepository.findByTenantEntity_UuidAndAddressUuid(tenantUuid,addressUuid);
        BeanUtils.copyProperties(addressEntity, addressValue);
        addressValue.setTenantUuid(tenantUuid);
        return addressValue;
    }

    @Override
    public AddressValue deleteAddress( String tenantUuid,String addressUuid) throws Exception {
        AddressValue addressValue=new AddressValue();
        addressRepository .softDelete(addressUuid);
        AddressEntity  addressEntity=addressRepository.findByTenantEntity_UuidAndAddressUuid(tenantUuid,addressUuid);
        BeanUtils.copyProperties(addressEntity, addressValue);
        return addressValue;
    }

    @Override
    public List<AddressValue> getAllCompanyAddress(String tenantUuid) throws Exception {
        List<AddressValue> addressValues = new ArrayList<>();
        AddressValue addressValue = null;
        List<AddressEntity> addressEntities = addressRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i = 0; i < addressEntities.size(); i++) {
            addressValue = new AddressValue();
            BeanUtils.copyProperties(addressEntities.get(i), addressValue);
            addressValue.setTenantUuid(tenantUuid);
            addressValues.add(addressValue);
        }
        return addressValues;
    }
}
