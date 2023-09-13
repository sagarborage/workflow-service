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

        addressEntity.toBuilder().id(addressRepository.findByUuid(addressValue.getAddressUuid()).getId()).build();
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
