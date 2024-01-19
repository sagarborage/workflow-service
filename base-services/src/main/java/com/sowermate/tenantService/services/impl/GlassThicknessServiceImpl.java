package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.GlassThicknessEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.GlassThicknessValue;
import com.sowermate.tenantService.repositories.GlassThicknessRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.GlassThicknessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class GlassThicknessServiceImpl implements GlassThicknessService {

    @Autowired
    private GlassThicknessRepository glassThicknessRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Override
    public GlassThicknessValue createGlassThickness(GlassThicknessValue glassThicknessValue) {

        TenantEntity tenantEntity = tenantRepository.findByUuid(glassThicknessValue.getTenantUuid());
        GlassThicknessEntity glassThicknessEntity = glassThicknessValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .build();
        return glassThicknessRepository.save(glassThicknessEntity).toDTO().toBuilder().tenantUuid(tenantEntity.getUuid()).build();
    }

    @Override
    public GlassThicknessValue editGlassThickness(GlassThicknessValue glassThicknessValue) {

        TenantEntity tenantEntity = tenantRepository.findByUuid(glassThicknessValue.getTenantUuid());
        GlassThicknessEntity tempGlassThicknessEntity = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(glassThicknessValue.getTenantUuid(),
                glassThicknessValue.getGlassThicknessUuid());
        GlassThicknessEntity glassThicknessEntity = glassThicknessValue.toEntity().toBuilder()
                .id(tempGlassThicknessEntity.getId())
                .tenantEntity(tenantEntity)
                .createdDateTime(tempGlassThicknessEntity.getCreatedDateTime())
                .createdBy(tempGlassThicknessEntity.getCreatedBy())
                .build();
        return glassThicknessRepository.save(glassThicknessEntity).toDTO().toBuilder().tenantUuid(tenantEntity.getUuid()).build();
    }

    @Override
    public GlassThicknessValue getGlassThickness(String tenantUuid, String glassThicknessUuid) {
        GlassThicknessEntity glassThicknessEntity = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid, glassThicknessUuid);
        return glassThicknessEntity.toDTO().toBuilder().tenantUuid(tenantUuid).build();
    }

    @Override
    public GlassThicknessValue deleteGlassThickness(String tenantUuid, String glassThicknessUuid) {
        glassThicknessRepository.softDelete(glassThicknessUuid);
        return glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid, glassThicknessUuid).toDTO();
    }

    @Override
    public List<GlassThicknessValue> getAllGlassThickness(String tenantUuid) {
        List<GlassThicknessEntity> glassThicknessEntities = glassThicknessRepository.findAllByTenantEntity_Uuid(tenantUuid);

        return glassThicknessEntities.stream().map(gte -> gte.toDTO().toBuilder().tenantUuid(tenantUuid).build()).collect(Collectors.toList());
    }
}
