package com.sowermate.workflow.service.services.impl;

import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.core.tenant.repositories.TenantRepository;
import com.sowermate.workflow.domain.entities.GlassTypeEntity;
import com.sowermate.workflow.domain.entities.value.GlassTypeValue;
import com.sowermate.workflow.persistence.repositories.GlassTypeRepository;
import com.sowermate.workflow.service.services.GlassTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class GlassTypeServiceImpl implements GlassTypeService {

    @Autowired
    private GlassTypeRepository glassTypeRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public GlassTypeValue createGlassType(GlassTypeValue glassTypeValue) {

        Tenant tenantEntity = tenantRepository.findByUuid(glassTypeValue.getTenantUuid());
        GlassTypeEntity glassTypeEntity = glassTypeValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .build();

        return glassTypeRepository.save(glassTypeEntity).toDTO();
    }

    @Override
    public GlassTypeValue editGlassType(GlassTypeValue glassTypeValue) {
        Tenant tenantEntity = tenantRepository.findByUuid(glassTypeValue.getTenantUuid());
        GlassTypeEntity tempGlassTypeEntity = glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(glassTypeValue.getTenantUuid(),
                glassTypeValue.getUuid());
        GlassTypeEntity glassTypeEntity = glassTypeValue.toEntity().toBuilder()
                .id(tempGlassTypeEntity.getId())
                .createdDateTime(tempGlassTypeEntity.getCreatedDateTime())
                .createdBy(tempGlassTypeEntity.getCreatedBy())
                .tenantEntity(tenantEntity)
                .version(tempGlassTypeEntity.getVersion())
                .build();
        return glassTypeRepository.save(glassTypeEntity).toDTO();
    }

    @Override
    public GlassTypeValue getGlassType(String tenantUuid, String glassTypeUuid) {
        return glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid, glassTypeUuid).toDTO();
    }


    @Override
    public GlassTypeValue deleteGlassType(String tenantUuid, String glassTypeUuid) {
        glassTypeRepository.softDelete(glassTypeUuid);
        GlassTypeEntity glassTypeEntity = glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid, glassTypeUuid);
        return glassTypeEntity.toDTO();
    }

    @Override
    public List<GlassTypeValue> getAllGlassType(String tenantUuid) {
        List<GlassTypeEntity> glassTypeEntities = glassTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return glassTypeEntities.stream().map(gte -> gte.toDTO()).collect(Collectors.toList());
    }
}

