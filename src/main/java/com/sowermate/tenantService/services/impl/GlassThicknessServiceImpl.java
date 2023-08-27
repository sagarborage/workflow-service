package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.GlassSpecificationEntity;
import com.sowermate.tenantService.entities.GlassThicknessEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.GlassThicknessValue;
import com.sowermate.tenantService.repositories.GlassThicknessRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.GlassThicknessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class GlassThicknessServiceImpl implements GlassThicknessService {

    @Autowired
    private GlassThicknessRepository glassThicknessRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Override
    public GlassThicknessValue createGlassThickness(GlassThicknessValue glassThicknessValue) throws Exception {

        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(glassThicknessValue.getTenantValue().getUuid());
        GlassThicknessEntity glassThicknessEntity = glassThicknessValue.toEntity().toBuilder()
                .glassThicknessUuid(CommonUtils.generateUUID())
                .tenantEntity(tenantEntity)
                .build();
/*        GlassThicknessEntity glassThicknessEntity = new GlassThicknessEntity();
        BeanUtils.copyProperties(glassThicknessValue, glassThicknessEntity);
        glassThicknessEntity.setGlassThicknessUuid(CommonUtils.generateUUID());
        glassThicknessEntity.setTenantEntity(tenantRepository.findByTenantUuid(glassThicknessValue.getTenantUuid()));
        BeanUtils.copyProperties(glassThicknessRepository.save(glassThicknessEntity), glassThicknessValue);*/
        return glassThicknessRepository.save(glassThicknessEntity).toDTO();
    }

    @Override
    public GlassThicknessValue editGlassThickness(GlassThicknessValue glassThicknessValue) throws Exception {

        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(glassThicknessValue.getTenantValue().getUuid());
        GlassThicknessEntity tempGlassThicknessEntity = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(glassThicknessValue.getTenantValue().getUuid(),
                glassThicknessValue.getGlassThicknessUuid());
        GlassThicknessEntity glassThicknessEntity = glassThicknessValue.toEntity().toBuilder()
                .glassThicknessId(tempGlassThicknessEntity.getGlassThicknessId())
                .tenantEntity(tenantEntity)
                .build();
/*        GlassThicknessEntity glassThicknessEntity = new GlassThicknessEntity();
        BeanUtils.copyProperties(glassThicknessValue, glassThicknessEntity);

        // Check that UUID is not null before searching for the tenant
        if (glassThicknessValue.getGlassThicknessUuid() != null) {
            GlassThicknessEntity matchingGlassThickness = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(glassThicknessValue.getTenantUuid(), glassThicknessValue.getGlassThicknessUuid());
            if (matchingGlassThickness != null) {
                glassThicknessEntity.setGlassThicknessId(matchingGlassThickness.getGlassThicknessId());
                glassThicknessEntity.setTenantEntity(tenantRepository.findByTenantUuid(glassThicknessValue.getTenantUuid()));
                BeanUtils.copyProperties(glassThicknessRepository.save(glassThicknessEntity), glassThicknessValue);
            } else {
                throw new Exception("No tenant found with UUID " + glassThicknessValue.getGlassThicknessUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }*/

        return glassThicknessRepository.save(glassThicknessEntity).toDTO();
    }

    @Override
    public GlassThicknessValue getGlassThickness(String tenantUuid, String glassThicknessUuid) throws Exception {
/*        GlassThicknessValue glassThicknessValue = new GlassThicknessValue();

        GlassThicknessEntity glassThicknessEntity = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid, glassThicknessUuid);
        BeanUtils.copyProperties(glassThicknessEntity, glassThicknessValue);
        glassThicknessValue.setTenantUuid(tenantUuid);*/
        GlassThicknessEntity glassThicknessEntity = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid, glassThicknessUuid);
        return glassThicknessEntity.toDTO();
    }

    @Override
    public GlassThicknessValue deleteGlassThickness(String tenantUuid, String glassThicknessUuid) throws Exception {
        glassThicknessRepository.softDelete(glassThicknessUuid);
/*        GlassThicknessEntity glassThicknessEntity = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid, glassThicknessUuid);
        BeanUtils.copyProperties(glassThicknessEntity, glassThicknessValue);
        glassThicknessValue.setTenantUuid(tenantUuid);*/
        return glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid, glassThicknessUuid).toDTO();
    }

    @Override
    public List<GlassThicknessValue> getAllGlassThickness(String tenantUuid) throws Exception {
/*        List<GlassThicknessValue> glassThicknessValues = new ArrayList<>();
        GlassThicknessValue glassThicknessValue = null;*/
        List<GlassThicknessEntity> glassThicknessEntities = glassThicknessRepository.findAllByTenantEntity_Uuid(tenantUuid);
/*        for (int i = 0; i < glassThicknessEntities.size(); i++) {
            glassThicknessValue = new GlassThicknessValue();
            BeanUtils.copyProperties(glassThicknessEntities.get(i), glassThicknessValue);
            glassThicknessValue.setTenantUuid(tenantUuid);
            glassThicknessValues.add(glassThicknessValue);
        }*/

        return glassThicknessEntities.stream().map(gte -> gte.toDTO()).collect(Collectors.toList());
    }
}
