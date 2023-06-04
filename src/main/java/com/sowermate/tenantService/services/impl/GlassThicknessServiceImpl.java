package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.GlassThicknessEntity;
import com.sowermate.tenantService.entities.value.GlassThicknessValue;
import com.sowermate.tenantService.repositories.GlassThicknessRepository;
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
    @Override
    public GlassThicknessValue createGlassThickness(GlassThicknessValue glassThicknessValue) throws Exception {
        GlassThicknessEntity  glassThicknessEntity=new GlassThicknessEntity();
        BeanUtils.copyProperties(glassThicknessValue, glassThicknessEntity);
        String randomGlassThicknessId= UUID.randomUUID().toString();
        glassThicknessEntity.setUuid(randomGlassThicknessId);
        BeanUtils.copyProperties(glassThicknessRepository.save(glassThicknessEntity), glassThicknessValue);
        return glassThicknessValue;
    }

    @Override
    public GlassThicknessValue editGlassThickness(GlassThicknessValue glassThicknessValue) throws Exception {
        GlassThicknessEntity glassThicknessEntity = new GlassThicknessEntity();
        BeanUtils.copyProperties(glassThicknessValue, glassThicknessEntity);

        // Check that UUID is not null before searching for the tenant
        if (glassThicknessValue.getUuid() != null) {
            List<GlassThicknessEntity> matchingGlassThickness = glassThicknessRepository.findByUuid(glassThicknessValue.getUuid());
            if (!matchingGlassThickness.isEmpty()) {
                glassThicknessEntity.setGlassThicknessId(matchingGlassThickness.get(0).getGlassThicknessId());
                BeanUtils.copyProperties(glassThicknessRepository.save(glassThicknessEntity), glassThicknessValue);
            } else {
                throw new Exception("No tenant found with UUID " + glassThicknessValue.getUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return glassThicknessValue;
    }

    @Override
    public GlassThicknessValue getGlassThickness(String uuid) throws Exception {
        GlassThicknessValue glassThicknessValue=new GlassThicknessValue();

        GlassThicknessEntity glassThicknessEntity =glassThicknessRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(glassThicknessEntity ,glassThicknessValue);
        return glassThicknessValue;
    }

    @Override
    public GlassThicknessValue deleteGlassThickness(int glassThicknessId) throws Exception {
        GlassThicknessValue glassThicknessValue=new GlassThicknessValue();
        glassThicknessRepository.softDelete(glassThicknessId);
       GlassThicknessEntity  glassThicknessEntity =glassThicknessRepository.findByGlassThicknessId(glassThicknessId) .get(0);
        BeanUtils.copyProperties(glassThicknessEntity ,glassThicknessValue);
        return  glassThicknessValue;
    }

    @Override
    public List<GlassThicknessValue> getAllGlassThickness() throws Exception {
        List<GlassThicknessValue> glassThicknessValues=new ArrayList<>();
        GlassThicknessValue glassThicknessValue=null;
        List<GlassThicknessEntity> glassThicknessEntities= glassThicknessRepository.findAll();
        for (int i=0; i <glassThicknessEntities.size(); i++){
            glassThicknessValue =new GlassThicknessValue();
            BeanUtils.copyProperties(glassThicknessEntities.get(i), glassThicknessValue);

            glassThicknessValues .add(glassThicknessValue);
        }

        return glassThicknessValues;
    }
}
