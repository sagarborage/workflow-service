package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.GlassTypeEntity;
import com.sowermate.tenantService.entities.value.GlassTypeValue;
import com.sowermate.tenantService.repositories.GlassTypeRepository;
import com.sowermate.tenantService.services.GlassTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName = { "Exception" })
public class GlassTypeServiceImpl  implements GlassTypeService {

    @Autowired
    GlassTypeRepository  glassTypeRepository;
    @Override
    public GlassTypeValue createGlassType(GlassTypeValue glassTypeValue) throws Exception {

        GlassTypeEntity glassTypeEntity=new GlassTypeEntity();
        BeanUtils.copyProperties(glassTypeValue, glassTypeEntity);
        String randomGlassTypeId= UUID.randomUUID().toString();
        glassTypeEntity.setUuid(randomGlassTypeId);
        BeanUtils.copyProperties(glassTypeRepository.save(glassTypeEntity), glassTypeValue);
        return glassTypeValue;
    }

    @Override
    public GlassTypeValue editGlassType(GlassTypeValue glassTypeValue) throws Exception {
        GlassTypeEntity glassTypeEntity = new GlassTypeEntity();
        BeanUtils.copyProperties(glassTypeValue, glassTypeEntity);

        // Check that UUID is not null before searching for the tenant
        if (glassTypeValue.getUuid() != null) {
            List<GlassTypeEntity> matchingGlassType = glassTypeRepository.findByUuid(glassTypeValue.getUuid());
            if (!matchingGlassType.isEmpty()) {
                glassTypeEntity.setGlassTypeId(matchingGlassType.get(0).getGlassTypeId());
                BeanUtils.copyProperties(glassTypeRepository.save(glassTypeEntity), glassTypeValue);
            } else {
                throw new Exception("No tenant found with UUID " + glassTypeValue.getUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return glassTypeValue;
    }

    @Override
    public GlassTypeValue getGlassType(String uuid) throws Exception {
        GlassTypeValue glassTypeValue=new GlassTypeValue();

        GlassTypeEntity glassTypeEntity =glassTypeRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(glassTypeEntity ,glassTypeValue);
        return glassTypeValue;
    }


    @Override
    public GlassTypeValue deleteGlassType(String uuid) throws Exception {
        GlassTypeValue glassTypeValue=new GlassTypeValue();
        glassTypeRepository.softDelete(uuid);
        GlassTypeEntity glassTypeEntity =glassTypeRepository.findByUuid(uuid) .get(0);
        BeanUtils.copyProperties(glassTypeEntity ,glassTypeValue);
        return  glassTypeValue;
    }

    @Override
    public List<GlassTypeValue> getAllGlassType() throws Exception {
        List<GlassTypeValue> glassTypeValues=new ArrayList<>();
        GlassTypeValue glassTypeValue=null;
        List<GlassTypeEntity> glassTypeEntities= glassTypeRepository.findAll();
        for (int i=0; i <glassTypeEntities.size(); i++){
            glassTypeValue =new GlassTypeValue();
            BeanUtils.copyProperties(glassTypeEntities.get(i), glassTypeValue);

            glassTypeValues.add(glassTypeValue);
        }

        return glassTypeValues;
    }
    }

