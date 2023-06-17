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

@Service
@Transactional(rollbackForClassName = { "Exception" })
public class AdditionalChargesServiceImpl extends CommonService implements AdditionalChargesService {
    @Autowired
    private AdditionalChargesRepository  additionalChargesRepository;

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public AdditionalChargesValue saveAdditionalCharges(AdditionalChargesValue additionalChargesValue) throws Exception {
        AdditionalChargesEntity additionalChargesEntity=new AdditionalChargesEntity();
        BeanUtils.copyProperties(additionalChargesValue, additionalChargesEntity);
        initCreate(additionalChargesEntity);
        additionalChargesEntity.setTenantDetailsEntities(tenantRepository.findByTenantId(additionalChargesValue.getTenantId()).get(0));
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
            initEdit(additionalChargesEntity);
            additionalChargesEntity.setTenantDetailsEntities(tenantRepository.findByTenantId(additionalChargesValue.getTenantId()).get(0));
            additionalChargesEntity.setAdditionalChargesId(additionalChargesRepository.findByUuid(additionalChargesValue.getUuid()).get(0).getAdditionalChargesId());
            BeanUtils.copyProperties(additionalChargesRepository.save(additionalChargesEntity), additionalChargesValue);
            return additionalChargesValue;

    }

    @Override
    public AdditionalChargesValue getAdditionalCharges(String uuid) throws Exception {
        AdditionalChargesValue  additionalChargesValue=new AdditionalChargesValue();

        AdditionalChargesEntity additionalChargesEntity =additionalChargesRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(additionalChargesEntity ,additionalChargesValue);
        return additionalChargesValue;
    }


    @Override
    public AdditionalChargesValue deleteAdditionalCharges(String uuid) throws Exception {
        AdditionalChargesValue additionalChargesValue=new AdditionalChargesValue();
        AdditionalChargesEntity additionalChargesEntity =additionalChargesRepository.deleteAdditionalByUuid(uuid) .get(0);
        BeanUtils.copyProperties(additionalChargesEntity ,additionalChargesValue);
        return  additionalChargesValue;
    }
}
