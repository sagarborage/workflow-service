package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.TenantValue;
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
    public TenantValue saveTenantDetails(TenantValue tenantValue) throws Exception {
        TenantEntity tenantEntity = new TenantEntity();
        BeanUtils.copyProperties(tenantValue, tenantEntity);
        String randomTenantUuid = UUID.randomUUID().toString();
        tenantEntity.setUuid(randomTenantUuid);
        BeanUtils.copyProperties(tenantRepository.save(tenantEntity), tenantValue);
        tenantValue.setTenantUuid(tenantEntity.getUuid());
        return tenantValue;
    }

    @Override
    public List<TenantValue> getAllTenantDetails() throws Exception {
        List<TenantValue> tenantValues = new ArrayList<>();
        TenantValue tenantValue = null;
        List<TenantEntity> tenantDetailsEntities = tenantRepository.findAll();
        for (int i = 0; i < tenantDetailsEntities.size(); i++) {
            tenantValue = new TenantValue();

            BeanUtils.copyProperties(tenantDetailsEntities.get(i), tenantValue);
            tenantValue.setTenantUuid(tenantDetailsEntities.get(i).getUuid());
            tenantValues.add(tenantValue);
        }
        return tenantValues;
    }

    @Override
    public TenantValue editTenantDetails(TenantValue tenantValue) throws Exception {
        TenantEntity tenantEntity = new TenantEntity();
        BeanUtils.copyProperties(tenantValue, tenantEntity);

        // Check that UUID is not null before searching for the tenant
        if (tenantValue.getTenantUuid() != null) {
            TenantEntity matchingTenant = tenantRepository.findByTenantUuid(tenantValue.getTenantUuid());
            if (matchingTenant != null) {
                tenantEntity.setTenantId(matchingTenant.getTenantId());
                BeanUtils.copyProperties(tenantRepository.save(tenantEntity), tenantValue);
                tenantValue.setTenantUuid(tenantEntity.getUuid());
            } else {
                throw new Exception("No tenant found with UUID " + tenantValue.getTenantUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return tenantValue;
    }


    @Override
    public TenantValue getTenantDetails(String tenantUuid) throws Exception {
        TenantValue tenantValue = new TenantValue();

        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(tenantUuid);
        if (tenantEntity == null) {
            return null;
        } else {
            BeanUtils.copyProperties(tenantEntity, tenantValue);
            tenantValue.setTenantUuid(tenantEntity.getUuid());
            return tenantValue;
        }
    }

    @Override
    public TenantValue deleteTenantDetails(String tenantUuid) throws Exception {
        TenantValue tenantValue = new TenantValue();
        tenantRepository.softDelete(tenantUuid);
        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(tenantUuid);
        BeanUtils.copyProperties(tenantEntity, tenantValue);
        tenantValue.setTenantUuid(tenantEntity.getUuid());
        return tenantValue;
    }

}
