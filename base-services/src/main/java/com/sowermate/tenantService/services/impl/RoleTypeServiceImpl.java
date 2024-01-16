package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.RoleTypeEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.RoleTypeValue;
import com.sowermate.tenantService.repositories.RoleTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.RoleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class RoleTypeServiceImpl implements RoleTypeService {

    @Autowired
    RoleTypeRepository RoleTypeRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Override
    public RoleTypeValue createRoleType(RoleTypeValue RoleTypeValue) {

        TenantEntity tenantEntity = tenantRepository.findByUuid(RoleTypeValue.getTenantUuid());
        RoleTypeEntity RoleTypeEntity = RoleTypeValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .build();

        return RoleTypeRepository.save(RoleTypeEntity).toDTO();
    }

    @Override
    public RoleTypeValue editRoleType(RoleTypeValue roleTypeValue) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(roleTypeValue.getTenantUuid());
        RoleTypeEntity tempRoleTypeEntity = RoleTypeRepository.findByTenantEntity_UuidAndRoleTypeUuid(roleTypeValue.getTenantUuid(),
                roleTypeValue.getUuid());
        RoleTypeEntity RoleTypeEntity = roleTypeValue.toEntity().toBuilder()
                .id(tempRoleTypeEntity.getId())
                .tenantEntity(tenantEntity)
                .createdDateTime(tempRoleTypeEntity.getCreatedDateTime())
                .createdBy(tempRoleTypeEntity.getCreatedBy())
                .build();
        return RoleTypeRepository.save(RoleTypeEntity).toDTO();
    }

    @Override
    public RoleTypeValue getRoleType(String tenantUuid, String RoleTypeUuid) {
        return RoleTypeRepository.findByTenantEntity_UuidAndRoleTypeUuid(tenantUuid, RoleTypeUuid).toDTO();
    }

    @Override
    public RoleTypeValue deleteRoleType(String tenantUuid, String RoleTypeUuid) {
        RoleTypeRepository.softDelete(RoleTypeUuid);
        RoleTypeEntity RoleTypeEntity = RoleTypeRepository.findByTenantEntity_UuidAndRoleTypeUuid(tenantUuid, RoleTypeUuid);
        return RoleTypeEntity.toDTO();
    }

    @Override
    public List<RoleTypeValue> getAllRoleType(String tenantUuid) {
        List<RoleTypeEntity> RoleTypeEntities = RoleTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return RoleTypeEntities.stream().map(gte -> gte.toDTO()).collect(Collectors.toList());
    }
}

