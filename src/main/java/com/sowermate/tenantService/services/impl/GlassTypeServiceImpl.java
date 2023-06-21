package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.GlassTypeEntity;
import com.sowermate.tenantService.entities.value.GlassTypeValue;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.GlassTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.GlassTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class GlassTypeServiceImpl implements GlassTypeService {

    @Autowired
    GlassTypeRepository glassTypeRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Override
    public GlassTypeValue createGlassType(GlassTypeValue glassTypeValue) throws Exception {

        GlassTypeEntity glassTypeEntity = new GlassTypeEntity();
        BeanUtils.copyProperties(glassTypeValue, glassTypeEntity);
        String randomGlassTypeId = UUID.randomUUID().toString();
        glassTypeEntity.setGlassTypeUuid(randomGlassTypeId);
        glassTypeEntity.setTenantEntity(tenantRepository.findByTenantUuid(glassTypeValue.getTenantUuid()));
        BeanUtils.copyProperties(glassTypeRepository.save(glassTypeEntity), glassTypeValue);
        return glassTypeValue;
    }

    @Override
    public GlassTypeValue editGlassType(GlassTypeValue glassTypeValue) throws Exception {
        GlassTypeEntity glassTypeEntity = new GlassTypeEntity();
        BeanUtils.copyProperties(glassTypeValue, glassTypeEntity);

        // Check that UUID is not null before searching for the tenant
        if (glassTypeValue.getGlassTypeUuid() != null) {
            GlassTypeEntity matchingGlassType = glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(glassTypeValue.getTenantUuid(), glassTypeValue.getGlassTypeUuid());
            if (matchingGlassType != null) {
                glassTypeEntity.setGlassTypeId(matchingGlassType.getGlassTypeId());
                glassTypeEntity.setTenantEntity(tenantRepository.findByTenantUuid(glassTypeValue.getTenantUuid()));
                BeanUtils.copyProperties(glassTypeRepository.save(glassTypeEntity), glassTypeValue);
            } else {
                throw new Exception("No glass type found with UUID " + glassTypeValue.getGlassTypeUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return glassTypeValue;
    }

    @Override
    public GlassTypeValue getGlassType(String tenantUuid, String glassTypeUuid) throws Exception {
        GlassTypeValue glassTypeValue = new GlassTypeValue();

        GlassTypeEntity glassTypeEntity = glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid, glassTypeUuid);
        BeanUtils.copyProperties(glassTypeEntity, glassTypeValue);
        glassTypeValue.setTenantUuid(tenantUuid);
        glassTypeValue.setGlassTypeUuid(glassTypeUuid);
        return glassTypeValue;
    }


    @Override
    public GlassTypeValue deleteGlassType(String tenantUuid, String glassTypeUuid) throws Exception {
        GlassTypeValue glassTypeValue = new GlassTypeValue();
        glassTypeRepository.softDelete(glassTypeUuid);
        GlassTypeEntity glassTypeEntity = glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid, glassTypeUuid);
        BeanUtils.copyProperties(glassTypeEntity, glassTypeValue);
        glassTypeValue.setTenantUuid(tenantUuid);
        return glassTypeValue;
    }

    @Override
    public List<GlassTypeValue> getAllGlassType(String tenantUuid) throws Exception {
        List<GlassTypeValue> glassTypeValues = new ArrayList<>();
        GlassTypeValue glassTypeValue = null;
        List<GlassTypeEntity> glassTypeEntities = glassTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i = 0; i < glassTypeEntities.size(); i++) {
            glassTypeValue = new GlassTypeValue();
            BeanUtils.copyProperties(glassTypeEntities.get(i), glassTypeValue);
            glassTypeValue.setTenantUuid(tenantUuid);
            glassTypeValues.add(glassTypeValue);
        }

        return glassTypeValues;
    }
}

