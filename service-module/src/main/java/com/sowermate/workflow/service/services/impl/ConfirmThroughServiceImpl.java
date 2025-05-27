package com.sowermate.workflow.service.services.impl;

import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.core.tenant.repositories.TenantRepository;
import com.sowermate.workflow.domain.entities.AdditionalChargesEntity;
import com.sowermate.workflow.domain.entities.ConfirmThroughEntity;
import com.sowermate.workflow.domain.entities.value.AdditionalChargesValue;
import com.sowermate.workflow.domain.entities.value.ConfirmThroughValue;
import com.sowermate.workflow.persistence.repositories.ConfirmThroughRepository;
import com.sowermate.workflow.service.services.ConfirmThroughService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ConfirmThroughServiceImpl implements ConfirmThroughService {

    @Autowired
    private ConfirmThroughRepository confirmThroughRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Override
    public ConfirmThroughValue createConfirmThrough(ConfirmThroughValue confirmThroughValue) {

        Tenant tenantEntity = tenantRepository.findByUuid(confirmThroughValue.getTenantUuid());


        ConfirmThroughEntity confirmThroughEntity = confirmThroughValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity).build();
        return confirmThroughRepository.save(confirmThroughEntity).toDTO();
    }

    @Override
    public List<ConfirmThroughValue> getAllConfirmThrough(String tenantUuid) {
        List<ConfirmThroughValue> confirmThroughValues = new ArrayList<>();
        ConfirmThroughValue confirmThroughValue = null;
        List<ConfirmThroughEntity> confirmThroughEntities = confirmThroughRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return confirmThroughEntities.stream().map(cte -> cte.toDTO()).collect(Collectors.toList());
    }

    @Override
    public ConfirmThroughValue editConfirmThrough(ConfirmThroughValue confirmThroughValue) {
        Tenant tenantEntity = tenantRepository.findByUuid(confirmThroughValue.getTenantUuid());

        ConfirmThroughEntity tempConfirmThroughEntity = confirmThroughRepository
                .findByTenantEntity_UuidAndConfirmThroughUuid(confirmThroughValue.getTenantUuid(), confirmThroughValue.getUuid());

        ConfirmThroughEntity confirmThroughEntity = confirmThroughValue.toEntity().toBuilder()
                .id(tempConfirmThroughEntity.getId())
                .tenantEntity(tenantEntity)
                .createdDateTime(tempConfirmThroughEntity.getCreatedDateTime())
                .createdBy(tempConfirmThroughEntity.getCreatedBy())
                .version(tempConfirmThroughEntity.getVersion())
                .build();
        return confirmThroughRepository.save(confirmThroughEntity).toDTO();
    }

    @Override
    public ConfirmThroughValue getConfirmThrough(String tenantUuid, String confirmThroughUuid) {
        return confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUuid, confirmThroughUuid).toDTO();
    }

    @Override
    public int deleteConfirmThrough(String tenantUuid, String confirmThroughUuid) {
        return confirmThroughRepository.deleteByConfirmThroughUuid(confirmThroughUuid);
    }
}
