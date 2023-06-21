package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AdditionalChargesEntity;
import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import com.sowermate.tenantService.repositories.AdditionalChargesRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.AdditionalChargesService;
import com.sowermate.tenantService.services.CommonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName = { "Exception" })
public class AdditionalChargesServiceImpl implements AdditionalChargesService {
    @Autowired
    private AdditionalChargesRepository  additionalChargesRepository;

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public AdditionalChargesValue saveAdditionalCharges(AdditionalChargesValue additionalChargesValue) throws Exception {
        AdditionalChargesEntity additionalChargesEntity=new AdditionalChargesEntity();
        BeanUtils.copyProperties(additionalChargesValue, additionalChargesEntity);
        String randomAdditionalChargesUuid= UUID.randomUUID().toString();
        additionalChargesEntity.setAdditionalChargesUuid(randomAdditionalChargesUuid);
        additionalChargesEntity.setTenantEntity(tenantRepository.findByTenantUuid(additionalChargesValue.getTenantUuid()));
        BeanUtils.copyProperties(additionalChargesRepository.save(additionalChargesEntity), additionalChargesValue);
        return additionalChargesValue;
    }

    @Override
    public List<AdditionalChargesValue> getAllAdditionalCharges() throws Exception {
        List<AdditionalChargesValue> additionalChargesValues=new ArrayList<>();
        AdditionalChargesValue additionalChargesValue=null;
        List<AdditionalChargesEntity> additionalChargesEntities= additionalChargesRepository.findAll();
        for (int i=0; i <additionalChargesEntities.size(); i++){
            additionalChargesValue =new AdditionalChargesValue();
            BeanUtils.copyProperties(additionalChargesEntities.get(i), additionalChargesValue);

            additionalChargesValues.add(additionalChargesValue);
        }

        return additionalChargesValues;
    }

    @Override
    public AdditionalChargesValue editAdditionalCharges(AdditionalChargesValue additionalChargesValue) throws Exception {

            AdditionalChargesEntity additionalChargesEntity=new AdditionalChargesEntity();
            BeanUtils.copyProperties(additionalChargesValue , additionalChargesEntity);
            additionalChargesEntity.setTenantEntity(tenantRepository.findByTenantUuid(additionalChargesValue.getTenantUuid()));
            additionalChargesEntity.setAdditionalChargesId(additionalChargesRepository.findByAdditionalChargesUuid(additionalChargesValue.getAdditionalChargesUuid()).getAdditionalChargesId());
            BeanUtils.copyProperties(additionalChargesRepository.save(additionalChargesEntity), additionalChargesValue);
            return additionalChargesValue;

    }

    @Override
    public AdditionalChargesValue getAdditionalCharges(String additionalChargesUuid) throws Exception {
        AdditionalChargesValue  additionalChargesValue=new AdditionalChargesValue();

        AdditionalChargesEntity additionalChargesEntity =additionalChargesRepository.findByAdditionalChargesUuid(additionalChargesUuid);
        BeanUtils.copyProperties(additionalChargesEntity ,additionalChargesValue);
        return additionalChargesValue;
    }


    @Override
    public AdditionalChargesValue deleteAdditionalCharges(String additionalChargesUuid) throws Exception {
        AdditionalChargesValue additionalChargesValue=new AdditionalChargesValue();
        AdditionalChargesEntity additionalChargesEntity =additionalChargesRepository.deleteByAdditionalChargesUuid(additionalChargesUuid) ;
        BeanUtils.copyProperties(additionalChargesEntity ,additionalChargesValue);
        return  additionalChargesValue;
    }
}
