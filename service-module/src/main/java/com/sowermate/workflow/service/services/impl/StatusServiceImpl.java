package com.sowermate.workflow.service.services.impl;


import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.core.tenant.repositories.TenantRepository;
import com.sowermate.core.tenant.services.TenantService;
import com.sowermate.workflow.domain.entities.StatusEntity;
import com.sowermate.workflow.domain.entities.value.StatusValue;
import com.sowermate.workflow.persistence.repositories.StatusRepository;
import com.sowermate.workflow.service.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TenantService tenantService;

    @Override
    public StatusValue createStatus(StatusValue statusValue) {
        Tenant tenantEntity = tenantService.getTenantEntity(statusValue.getTenantUuid());
        StatusEntity statusEntity = statusValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .build();
        return statusRepository.save(statusEntity).toDTO();
    }

    @Override
    public StatusValue editStatus(StatusValue statusValue) throws Exception {
        Tenant tenantEntity = tenantService.getTenantEntity(statusValue.getTenantUuid());
        StatusEntity tempStatusEntity = statusRepository.findByTenantEntity_UuidAndStatusUuid(statusValue.getTenantUuid(), statusValue.getUuid());
        StatusEntity statusEntity = statusValue.toEntity().toBuilder()
                .id(tempStatusEntity.getId())
                .tenantEntity(tenantEntity)
                .name(tempStatusEntity.getName())
                .createdDateTime(tempStatusEntity.getCreatedDateTime())
                .createdBy(tempStatusEntity.getCreatedBy())
                .version(tempStatusEntity.getVersion())
                .build();
        return statusRepository.save(statusEntity).toDTO().toBuilder().tenantUuid(tenantEntity.getUuid()).build();
    }

    @Override
    public StatusValue getStatus(String tenantUuid, String statusUuid) {
        StatusEntity statusEntity = statusRepository.findByTenantEntity_UuidAndStatusUuid(tenantUuid, statusUuid);
        return statusEntity.toDTO();
    }

    @Override
    public StatusValue deleteStatus(String tenantUuid, String statusUuid) {
        StatusEntity statusEntity = statusRepository.findByTenantEntity_UuidAndStatusUuid(tenantUuid, statusUuid);
        StatusValue statusValue = statusEntity.toDTO();
        statusRepository.delete(statusEntity);
        return statusValue;
    }


    @Override
    public List<StatusValue> getAllStatus(String tenantUuid) {
        List<StatusEntity> statusEntities = statusRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return statusEntities.stream().map(statusEntity -> statusEntity.toDTO()).collect(Collectors.toList());
    }
}
