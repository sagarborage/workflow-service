package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AdditionalChargesEntity;
import com.sowermate.tenantService.entities.AddressTypeEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.AdditionalChargesRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.AdditionalChargesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class AdditionalChargesServiceImpl implements AdditionalChargesService {
    @Autowired
    private AdditionalChargesRepository additionalChargesRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public AdditionalChargesValue saveAdditionalCharges(AdditionalChargesValue additionalChargesValue) {
        AdditionalChargesEntity additionalChargesEntity = additionalChargesValue.toEntity().toBuilder()
                .tenantEntity(getTenantEntity(additionalChargesValue.getTenantUuid())).build();
            return additionalChargesRepository.save(additionalChargesEntity).toDTO();
    }

    @Override
    public List<AdditionalChargesValue> getAllAdditionalCharges(String tenantUuid) {
        TenantEntity tenantEntity = getTenantEntity(tenantUuid);
        List<AdditionalChargesEntity> additionalChargesEntities = additionalChargesRepository.findByTenantEntityId(tenantEntity.getId());
        return additionalChargesEntities.stream().map(additionalCharges -> additionalCharges.toDTO()).collect(Collectors.toList());
    }

    @Override
    public AdditionalChargesValue editAdditionalCharges(AdditionalChargesValue additionalChargesValue) {

        AdditionalChargesEntity additionalChargesEntityTemp = getAdditionalChargesEntity(additionalChargesValue.getTenantUuid(),
                additionalChargesValue.getUuid());
        AdditionalChargesEntity additionalChargesEntity = additionalChargesValue.toEntity().toBuilder()
                .tenantEntity(getTenantEntity(additionalChargesValue.getTenantUuid()))
                .id(additionalChargesEntityTemp.getId())
                .createdDateTime(additionalChargesEntityTemp.getCreatedDateTime())
                .createdBy(additionalChargesEntityTemp.getCreatedBy())
                .build();
        return additionalChargesRepository.save(additionalChargesEntity).toDTO();
    }

    @Override
    public AdditionalChargesValue getAdditionalCharges(String tenantUuid, String additionalChargesUuid) {
        AdditionalChargesEntity additionalChargesEntity =getAdditionalChargesEntity(tenantUuid, additionalChargesUuid);
        return additionalChargesEntity.toDTO();
    }

    @Override
    public int deleteAdditionalCharges(String tenantUuid, String additionalChargesUuid) {
        AdditionalChargesEntity additionalChargesEntity =getAdditionalChargesEntity(tenantUuid, additionalChargesUuid);
        return additionalChargesRepository.deleteByAdditionalChargesUuid(additionalChargesUuid);
    }

    public TenantEntity getTenantEntity(String tenantUuid){
        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUuid);
        if (tenantEntity==null){
            throw new ResourceNotFoundException("TenantEntity","tenantUuid",tenantUuid);
        }
        return tenantEntity;
    }

    public AdditionalChargesEntity getAdditionalChargesEntity(String tenantUuid, String additionalChargesEntityUuid){
        AdditionalChargesEntity additionalChargesEntity = additionalChargesRepository.findByTenantEntity_UuidAndAdditionalChargesUuid(tenantUuid,additionalChargesEntityUuid);
        if (additionalChargesEntity==null){
            throw new ResourceNotFoundException("AdditionalChargesEntity","tenantUuid or additionalChargesEntityUuid",tenantUuid+" or "+ additionalChargesEntityUuid);
        }
        return additionalChargesEntity;
    }
}
