package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.GlassSpecificationEntity;
import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import com.sowermate.tenantService.repositories.GlassSpecificationRepository;
import com.sowermate.tenantService.services.GlassSpecificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName = { "Exception" })
public class GlassSpecificationServiceImpl implements GlassSpecificationService {

    @Autowired
    private GlassSpecificationRepository glassSpecificationRepository;

    @Override
    public GlassSpecificationValue createGlassSpecification(GlassSpecificationValue glassSpecificationValue) throws Exception {
        GlassSpecificationEntity glassSpecificationEntity=new GlassSpecificationEntity();
        BeanUtils.copyProperties(glassSpecificationValue, glassSpecificationEntity);
        String randomGlassSpecificationId= UUID.randomUUID().toString();
        glassSpecificationEntity.setGlassSpecificationUuid(randomGlassSpecificationId);
        BeanUtils.copyProperties(glassSpecificationRepository.save(glassSpecificationEntity), glassSpecificationValue);
        return glassSpecificationValue;
    }

    @Override
    public GlassSpecificationValue editGlassSpecification(GlassSpecificationValue glassSpecificationValue) throws Exception {
        GlassSpecificationEntity glassSpecificationEntity = new GlassSpecificationEntity();
        BeanUtils.copyProperties(glassSpecificationValue, glassSpecificationEntity);

        // Check that UUID is not null before searching for the tenant
        if (glassSpecificationValue.getGlassSpecificationUuid() != null) {
            GlassSpecificationEntity matchingGlassSpecification = glassSpecificationRepository.findByGlassSpecificationUuid(glassSpecificationValue.getGlassSpecificationUuid());
            if (matchingGlassSpecification !=null)  {
                glassSpecificationEntity.setGlassSpecificationId(matchingGlassSpecification.getGlassSpecificationId());
                BeanUtils.copyProperties(glassSpecificationRepository.save(glassSpecificationEntity), glassSpecificationValue);
            } else {
                throw new Exception("No tenant found with UUID " + glassSpecificationValue.getGlassSpecificationUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return glassSpecificationValue;
    }

    @Override
    public GlassSpecificationValue getGlassSpecification(String glassSpecificationUuid) throws Exception {
        GlassSpecificationValue glassSpecificationValue=new GlassSpecificationValue();

        GlassSpecificationEntity glassSpecificationEntity =glassSpecificationRepository.findByGlassSpecificationUuid(glassSpecificationUuid);
        BeanUtils.copyProperties(glassSpecificationEntity ,glassSpecificationValue);
        return glassSpecificationValue;
    }

    @Override
    public GlassSpecificationValue deleteGlassSpecification(String glassSpecificationUuid) throws Exception {
        GlassSpecificationValue glassSpecificationValue=new GlassSpecificationValue();
        glassSpecificationRepository.softDelete(glassSpecificationUuid);
        GlassSpecificationEntity glassSpecificationEntity =glassSpecificationRepository.findByGlassSpecificationUuid(glassSpecificationUuid);
        BeanUtils.copyProperties(glassSpecificationEntity ,glassSpecificationValue);
        return  glassSpecificationValue;
    }

    @Override
    public List<GlassSpecificationValue> getAllGlassSpecification() throws Exception {
        List<GlassSpecificationValue> glassSpecificationValues=new ArrayList<>();
        GlassSpecificationValue glassSpecificationValue=null;
        List<GlassSpecificationEntity> glassSpecificationEntities= glassSpecificationRepository.findAll();
        for (int i=0; i <glassSpecificationEntities.size(); i++){
            glassSpecificationValue =new GlassSpecificationValue();
            BeanUtils.copyProperties(glassSpecificationEntities.get(i), glassSpecificationValue);

            glassSpecificationValues.add(glassSpecificationValue);
        }

        return glassSpecificationValues;
    }
}
