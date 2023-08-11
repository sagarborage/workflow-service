package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.GlassThicknessEntity;
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

@Service
@Transactional(rollbackForClassName = { "Exception" })
public class GlassThicknessServiceImpl implements GlassThicknessService {

    @Autowired
    private GlassThicknessRepository glassThicknessRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Override
    public GlassThicknessValue createGlassThickness(GlassThicknessValue glassThicknessValue) throws Exception {
        GlassThicknessEntity  glassThicknessEntity=new GlassThicknessEntity();
        BeanUtils.copyProperties(glassThicknessValue, glassThicknessEntity);
        glassThicknessEntity.setGlassThicknessUuid(CommonUtils.generateUUID());
        glassThicknessEntity.setTenantEntity(tenantRepository.findByTenantUuid(glassThicknessValue.getTenantUuid()));
        BeanUtils.copyProperties(glassThicknessRepository.save(glassThicknessEntity), glassThicknessValue);
        return glassThicknessValue;
    }

    @Override
    public GlassThicknessValue editGlassThickness(GlassThicknessValue glassThicknessValue) throws Exception {
        GlassThicknessEntity glassThicknessEntity = new GlassThicknessEntity();
        BeanUtils.copyProperties(glassThicknessValue, glassThicknessEntity);

        // Check that UUID is not null before searching for the tenant
        if (glassThicknessValue.getGlassThicknessUuid() != null) {
            GlassThicknessEntity matchingGlassThickness = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(glassThicknessValue.getTenantUuid(), glassThicknessValue.getGlassThicknessUuid());
            if (matchingGlassThickness!=null) {
                glassThicknessEntity.setGlassThicknessId(matchingGlassThickness.getGlassThicknessId());
                glassThicknessEntity.setTenantEntity(tenantRepository.findByTenantUuid(glassThicknessValue.getTenantUuid()));
                BeanUtils.copyProperties(glassThicknessRepository.save(glassThicknessEntity), glassThicknessValue);
            } else {
                throw new Exception("No tenant found with UUID " + glassThicknessValue.getGlassThicknessUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return glassThicknessValue;
    }

    @Override
    public GlassThicknessValue getGlassThickness(String tenantUuid, String glassThicknessUuid) throws Exception {
        GlassThicknessValue glassThicknessValue=new GlassThicknessValue();

        GlassThicknessEntity glassThicknessEntity =glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid, glassThicknessUuid);
        BeanUtils.copyProperties(glassThicknessEntity ,glassThicknessValue);
        glassThicknessValue.setTenantUuid(tenantUuid);
        return glassThicknessValue;
    }

    @Override
    public GlassThicknessValue deleteGlassThickness(String tenantUuid, String glassThicknessUuid) throws Exception {
        GlassThicknessValue glassThicknessValue=new GlassThicknessValue();
        glassThicknessRepository.softDelete(glassThicknessUuid);
       GlassThicknessEntity  glassThicknessEntity =glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid, glassThicknessUuid);
        BeanUtils.copyProperties(glassThicknessEntity ,glassThicknessValue);
        glassThicknessValue.setTenantUuid(tenantUuid);
        return  glassThicknessValue;
    }

    @Override
    public List<GlassThicknessValue> getAllGlassThickness(String tenantUuid) throws Exception {
        List<GlassThicknessValue> glassThicknessValues=new ArrayList<>();
        GlassThicknessValue glassThicknessValue=null;
        List<GlassThicknessEntity> glassThicknessEntities= glassThicknessRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i=0; i <glassThicknessEntities.size(); i++){
            glassThicknessValue =new GlassThicknessValue();
            BeanUtils.copyProperties(glassThicknessEntities.get(i), glassThicknessValue);
            glassThicknessValue.setTenantUuid(tenantUuid);
            glassThicknessValues .add(glassThicknessValue);
        }

        return glassThicknessValues;
    }
}
