package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AdditionalChargesEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
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
                .tenantEntity(tenantRepository.findByUuid(additionalChargesValue.getTenantUuid())).build();
            return additionalChargesRepository.save(additionalChargesEntity).toDTO();
    }

    @Override
    public List<AdditionalChargesValue> getAllAdditionalCharges(String tenantUuid) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUuid);
        List<AdditionalChargesEntity> additionalChargesEntities = additionalChargesRepository.findByTenantEntityId(tenantEntity.getId());
        return additionalChargesEntities.stream().map(additionalCharges -> additionalCharges.toDTO()).collect(Collectors.toList());
    }

    @Override
    public AdditionalChargesValue editAdditionalCharges(AdditionalChargesValue additionalChargesValue) {
        AdditionalChargesEntity additionalChargesEntity = additionalChargesValue.toEntity().toBuilder()
                .tenantEntity(tenantRepository.findByUuid(additionalChargesValue.getTenantUuid()))
                .id(additionalChargesRepository.findByTenantEntity_UuidAndAdditionalChargesUuid(additionalChargesValue.getTenantUuid(),
                        additionalChargesValue.getAdditionalChargesUuid()).getId())
                .build();
        return additionalChargesRepository.save(additionalChargesEntity).toDTO();
    }

    @Override
    public AdditionalChargesValue getAdditionalCharges(String tenantUuid, String additionalChargesUuid) {
        AdditionalChargesEntity additionalChargesEntity = additionalChargesRepository
                .findByTenantEntity_UuidAndAdditionalChargesUuid(tenantUuid, additionalChargesUuid);
        return additionalChargesEntity.toDTO();
    }

    @Override
    public int deleteAdditionalCharges(String tenantUuid, String additionalChargesUuid) {
        return additionalChargesRepository.deleteByAdditionalChargesUuid(additionalChargesUuid);
    }
}
