package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.GlassSpecificationEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import com.sowermate.tenantService.repositories.GlassSpecificationRepository;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.GlassSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class GlassSpecificationServiceImpl implements GlassSpecificationService {

    @Autowired
    private GlassSpecificationRepository glassSpecificationRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Override
    public GlassSpecificationValue createGlassSpecification(GlassSpecificationValue glassSpecificationValue) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(glassSpecificationValue.getTenantUuid());

        GlassSpecificationEntity glassSpecificationEntity = glassSpecificationValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .build();
        return glassSpecificationRepository.save(glassSpecificationEntity).toDTO().toBuilder().tenantUuid(tenantEntity.getUuid()).build();
    }

    @Override
    public GlassSpecificationValue editGlassSpecification(GlassSpecificationValue glassSpecificationValue) {

        TenantEntity tenantEntity = tenantRepository.findByUuid(glassSpecificationValue.getTenantUuid());
        GlassSpecificationEntity tempGlassSpecificationEntity = glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(glassSpecificationValue.getTenantUuid(),
                glassSpecificationValue.getGlassSpecificationUuid());

        GlassSpecificationEntity glassSpecificationEntity = glassSpecificationValue.toEntity().toBuilder()
                .id(tempGlassSpecificationEntity.getId())
                .tenantEntity(tenantEntity)
                .build();
        return glassSpecificationRepository.save(glassSpecificationEntity).toDTO().toBuilder().tenantUuid(tenantEntity.getUuid()).build();
    }

    @Override
    public GlassSpecificationValue getGlassSpecification(String tenantUuid, String glassSpecificationUuid) {
        return glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid,
                glassSpecificationUuid).toDTO();
    }

    @Override
    public GlassSpecificationValue deleteGlassSpecification(String tenantUuid, String glassSpecificationUuid) {
        glassSpecificationRepository.softDelete(glassSpecificationUuid);
        GlassSpecificationEntity glassSpecificationEntity = glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid, glassSpecificationUuid);
        return glassSpecificationEntity.toDTO();
    }

    @Override
    public List<GlassSpecificationValue> getAllGlassSpecification(String tenantUuid) {
        List<GlassSpecificationEntity> glassSpecificationEntities = glassSpecificationRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return glassSpecificationEntities.stream().map(gse -> gse.toDTO().toBuilder().tenantUuid(tenantUuid).build()).collect(Collectors.toList());
    }
}
