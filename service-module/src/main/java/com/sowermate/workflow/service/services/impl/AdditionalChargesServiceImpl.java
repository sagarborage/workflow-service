package com.sowermate.workflow.service.services.impl;

import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.core.tenant.repositories.TenantRepository;
import com.sowermate.workflow.domain.entities.AdditionalChargesEntity;
import com.sowermate.workflow.domain.entities.value.AdditionalChargesValue;
import com.sowermate.workflow.persistence.repositories.AdditionalChargesRepository;
import com.sowermate.workflow.service.services.AdditionalChargesService;
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
        Tenant tenantEntity = tenantRepository.findByUuid(tenantUuid);
        List<AdditionalChargesEntity> additionalChargesEntities = additionalChargesRepository.findByTenantEntityId(tenantEntity.getId());
        return additionalChargesEntities.stream().map(additionalCharges -> additionalCharges.toDTO()).collect(Collectors.toList());
    }

    @Override
    public AdditionalChargesValue editAdditionalCharges(AdditionalChargesValue additionalChargesValue) {

        AdditionalChargesEntity additionalChargesEntityTemp = additionalChargesRepository.findByTenantEntity_UuidAndAdditionalChargesUuid(additionalChargesValue.getTenantUuid(),
                additionalChargesValue.getUuid());
        AdditionalChargesEntity additionalChargesEntity = additionalChargesValue.toEntity().toBuilder()
                .tenantEntity(tenantRepository.findByUuid(additionalChargesValue.getTenantUuid()))
                .id(additionalChargesEntityTemp.getId())
                .createdDateTime(additionalChargesEntityTemp.getCreatedDateTime())
                .createdBy(additionalChargesEntityTemp.getCreatedBy())
                .version(additionalChargesEntityTemp.getVersion())
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
