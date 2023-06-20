package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.TenantEntity;
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
@Transactional(rollbackForClassName = {"Exception"})
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public TenantDetailsValue saveTenantDetails(TenantDetailsValue tenantDetailsValue) throws Exception {
        TenantEntity tenantEntity = new TenantEntity();
        BeanUtils.copyProperties(tenantDetailsValue, tenantEntity);
        String randomTenantId = UUID.randomUUID().toString();
        tenantEntity.setUuid(randomTenantId);
        BeanUtils.copyProperties(tenantRepository.save(tenantEntity), tenantDetailsValue);
        return tenantDetailsValue;
    }

    @Override
    public List<TenantDetailsValue> getAllTenantDetails() throws Exception {
        List<TenantDetailsValue> tenantDetailsValues = new ArrayList<>();
        TenantDetailsValue tenantDetailsValue = null;
        List<TenantEntity> tenantDetailsEntities = tenantRepository.findAll();
        for (int i = 0; i < tenantDetailsEntities.size(); i++) {
            tenantDetailsValue = new TenantDetailsValue();

            BeanUtils.copyProperties(tenantDetailsEntities.get(i), tenantDetailsValue);
            tenantDetailsValues.add(tenantDetailsValue);
        }
        return tenantDetailsValues;
    }

    @Override
    public TenantDetailsValue editTenantDetails(TenantDetailsValue tenantDetailsValue) throws Exception {
        TenantEntity tenantEntity = new TenantEntity();
        BeanUtils.copyProperties(tenantDetailsValue, tenantEntity);

        // Check that UUID is not null before searching for the tenant
        if (tenantDetailsValue.getUuid() != null) {
            TenantEntity matchingTenant = tenantRepository.findByUuid(tenantDetailsValue.getUuid());
            if (matchingTenant != null) {
                tenantEntity.setTenantId(matchingTenant.getTenantId());
                BeanUtils.copyProperties(tenantRepository.save(tenantEntity), tenantDetailsValue);
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
        TenantDetailsValue tenantDetailsValue = new TenantDetailsValue();

        TenantEntity tenantEntity = tenantRepository.findByUuid(uuid);
        BeanUtils.copyProperties(tenantEntity, tenantDetailsValue);
        return tenantDetailsValue;
    }

    @Override
    public TenantDetailsValue deleteTenantDetails(String uuid) throws Exception {
        TenantDetailsValue tenantDetailsValue = new TenantDetailsValue();
        tenantRepository.softDelete(uuid);
        TenantEntity tenantEntity = tenantRepository.findByUuid(uuid);
        BeanUtils.copyProperties(tenantEntity, tenantDetailsValue);
        return tenantDetailsValue;
    }

}
