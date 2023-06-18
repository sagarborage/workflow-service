package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.TenantDetailsEntity;
import com.sowermate.tenantService.entities.value.TenantDetailsValue;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.TenantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName = { "Exception" })
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public TenantDetailsValue saveTenantDetails(TenantDetailsValue tenantDetailsValue) throws Exception {
        TenantDetailsEntity tenantDetailsEntity=new TenantDetailsEntity();
        BeanUtils.copyProperties(tenantDetailsValue, tenantDetailsEntity);
        String randomTenantId= UUID.randomUUID().toString();
       tenantDetailsEntity.setUuid(randomTenantId);
        BeanUtils.copyProperties(tenantRepository.save(tenantDetailsEntity), tenantDetailsValue);
        return tenantDetailsValue;
    }

    @Override
    public List<TenantDetailsValue> getAllTenantDetails() throws Exception  {
     List<TenantDetailsValue> tenantDetailsValues=new ArrayList<>();
     TenantDetailsValue tenantDetailsValue=null;
     List<TenantDetailsEntity> tenantDetailsEntities= tenantRepository.findAll();
     for (int i=0; i <tenantDetailsEntities.size(); i++){
       tenantDetailsValue =new TenantDetailsValue();

       BeanUtils.copyProperties(tenantDetailsEntities.get(i), tenantDetailsValue);
       tenantDetailsValues.add(tenantDetailsValue);
     }
        return tenantDetailsValues;
    }

    @Override
    public TenantDetailsValue editTenantDetails(TenantDetailsValue tenantDetailsValue) throws Exception {
        TenantDetailsEntity tenantDetailsEntity = new TenantDetailsEntity();
        BeanUtils.copyProperties(tenantDetailsValue, tenantDetailsEntity);

        // Check that UUID is not null before searching for the tenant
        if (tenantDetailsValue.getUuid() != null) {
            List<TenantDetailsEntity> matchingTenants = tenantRepository.findByUuid(tenantDetailsValue.getUuid());
            if (!matchingTenants.isEmpty()) {
                tenantDetailsEntity.setTenantId(matchingTenants.get(0).getTenantId());
                BeanUtils.copyProperties(tenantRepository.save(tenantDetailsEntity), tenantDetailsValue);
            } else {
                throw new Exception("No tenant found with UUID " + tenantDetailsValue.getUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return tenantDetailsValue;
    }




    @Override
    public TenantDetailsValue getTenantDetails(String uuid) throws Exception {
    TenantDetailsValue  tenantDetailsValue=new TenantDetailsValue();

    TenantDetailsEntity tenantDetailsEntity =tenantRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(tenantDetailsEntity ,tenantDetailsValue);
        return tenantDetailsValue;
    }

    @Override
    public TenantDetailsValue deleteTenantDetails(String uuid) throws Exception {
        TenantDetailsValue tenantDetailsValue=new TenantDetailsValue();
        tenantRepository.softDelete(uuid);
        TenantDetailsEntity tenantDetailsEntity =tenantRepository.findByUuid(uuid) .get(0);
        BeanUtils.copyProperties(tenantDetailsEntity ,tenantDetailsValue);
        return  tenantDetailsValue;
    }

}
