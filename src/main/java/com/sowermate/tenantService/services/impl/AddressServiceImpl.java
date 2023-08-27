package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AddressEntity;
import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.repositories.AddressRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
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
        AddressEntity addressEntity = addressValue.toEntity();
        addressEntity.toBuilder().addressUuid(CommonUtils.generateUUID());
        //addressEntity.setTenantEntity(tenantRepository.findByTenantUuid(addressValue.getTenantUuid()));
        //BeanUtils.copyProperties(addressRepository.save(addressEntity), addressValue);
        return addressRepository.save(addressEntity).toDTO();
    }

    @Override
    public AddressValue editAddress(AddressValue addressValue) throws Exception {
        AddressEntity  addressEntity = addressValue.toEntity();
        //BeanUtils.copyProperties(addressValue , addressEntity);

        addressEntity.toBuilder().addressId(addressRepository.findByAddressUuid(addressValue.getAddressUuid()).getAddressId()).build();
        //addressEntity.setTenantEntity(tenantRepository.findByTenantUuid(addressValue.getTenantUuid()));
        BeanUtils.copyProperties(addressRepository.save(addressEntity), addressValue);
        return addressRepository.save(addressEntity).toDTO();
    }

    @Override
    public AddressValue getAddress( String tenantUuid, String addressUuid) throws Exception {
        //AddressValue addressValue = new AddressValue();
        AddressEntity addressEntity = addressRepository.findByAddressUuid(addressUuid);
        //BeanUtils.copyProperties(addressEntity, addressValue);
        //addressValue.setTenantUuid(tenantUuid);
        return addressEntity.toDTO();
    }

    @Override
    public AddressValue deleteAddress( String tenantUuid,String addressUuid) throws Exception {
        //AddressValue addressValue=new AddressValue();
        addressRepository.softDelete(addressUuid);
        AddressEntity  addressEntity = addressRepository.findByAddressUuid(addressUuid);
        //BeanUtils.copyProperties(addressEntity, addressValue);
        return addressEntity.toDTO();
    }

    @Override
    public List<AddressValue> getAllCompanyAddress(String tenantUuid) throws Exception {
        List<AddressValue> addressValues = new ArrayList<>();
/*        AddressValue addressValue = null;
        List<AddressEntity> addressEntities = addressRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i = 0; i < addressEntities.size(); i++) {
            addressValue = new AddressValue();
            BeanUtils.copyProperties(addressEntities.get(i), addressValue);
            addressValue.setTenantUuid(tenantUuid);
            addressValues.add(addressValue);
        }*/
        return addressValues;
    }
}
