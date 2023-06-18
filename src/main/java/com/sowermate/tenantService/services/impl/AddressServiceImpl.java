package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AddressEntity;
import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.repositories.AddressRepository;
import com.sowermate.tenantService.services.AddressService;
import com.sowermate.tenantService.services.CommonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(rollbackForClassName= {"Exception"})
public class AddressServiceImpl extends CommonService implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Override
    public AddressValue createAddress(AddressValue addressValue) throws Exception {
        AddressEntity addressEntity=new AddressEntity();
        BeanUtils.copyProperties(addressValue ,addressEntity);
        initCreate(addressEntity);
        BeanUtils.copyProperties(addressRepository.save(addressEntity), addressValue);
        return addressValue;
    }

    @Override
    public AddressValue editAddress(AddressValue addressValue) throws Exception {
        AddressEntity  addressEntity=new AddressEntity();
        BeanUtils.copyProperties(addressValue , addressEntity);
        initEdit(addressEntity);
        addressEntity.setAddressId(addressRepository.findByUuid(addressValue.getUuid()).get(0).getAddressId());
        BeanUtils.copyProperties(addressRepository.save(addressEntity), addressValue);
        return addressValue;
    }

    @Override
    public AddressValue getAddress(String uuid) throws Exception {
        AddressValue addressValue =new AddressValue();
        AddressEntity addressEntity=addressRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(addressEntity, addressValue);
        return addressValue;
    }

    @Override
    public AddressValue deleteAddress( String uuid) throws Exception {
        AddressValue addressValue=new AddressValue();
        addressRepository .softDelete(uuid);
        AddressEntity  addressEntity=addressRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(addressEntity, addressValue);
        return addressValue;
    }

    @Override
    public List<AddressValue> getAllCompanyAddress() throws Exception {
        List<AddressValue> addressValues = new ArrayList<>();
        AddressValue addressValue = null;
        List<AddressEntity> addressEntities = addressRepository.findAll();
        for (int i = 0; i < addressEntities.size(); i++) {
            addressValue = new AddressValue();
            BeanUtils.copyProperties(addressEntities.get(i), addressValue);
            addressValues.add(addressValue);
        }
        return addressValues;
    }
}
