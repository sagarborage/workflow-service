package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.GlassSpecificationEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import com.sowermate.tenantService.repositories.GlassSpecificationRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
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

    @Autowired
    TenantRepository tenantRepository;

    @Override
    public GlassSpecificationValue createGlassSpecification(GlassSpecificationValue glassSpecificationValue) throws Exception {
        GlassSpecificationEntity glassSpecificationEntity=new GlassSpecificationEntity();
        BeanUtils.copyProperties(glassSpecificationValue, glassSpecificationEntity);
        String randomGlassSpecificationId= UUID.randomUUID().toString();
        glassSpecificationEntity.setGlassSpecificationUuid(randomGlassSpecificationId);
        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(glassSpecificationValue.getTenantUuid());
        if (tenantEntity != null) {
            glassSpecificationEntity.setTenantEntity(tenantEntity);
            BeanUtils.copyProperties(glassSpecificationRepository.save(glassSpecificationEntity), glassSpecificationValue);
            return glassSpecificationValue;
        }
        return null;
    }

    @Override
    public GlassSpecificationValue editGlassSpecification(GlassSpecificationValue glassSpecificationValue) throws Exception {
        GlassSpecificationEntity glassSpecificationEntity = new GlassSpecificationEntity();
        BeanUtils.copyProperties(glassSpecificationValue, glassSpecificationEntity);

        // Check that UUID is not null before searching for the tenant
        if (glassSpecificationValue.getGlassSpecificationUuid() != null) {
            GlassSpecificationEntity matchingGlassSpecification = glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(glassSpecificationValue.getTenantUuid(), glassSpecificationValue.getGlassSpecificationUuid());
            if (matchingGlassSpecification !=null)  {
                glassSpecificationEntity.setGlassSpecificationId(matchingGlassSpecification.getGlassSpecificationId());
                glassSpecificationEntity.setTenantEntity(tenantRepository.findByTenantUuid(glassSpecificationValue.getTenantUuid()));
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
    public GlassSpecificationValue getGlassSpecification(String tenantUuid, String glassSpecificationUuid) throws Exception {
        GlassSpecificationValue glassSpecificationValue=new GlassSpecificationValue();

        GlassSpecificationEntity glassSpecificationEntity =glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid, glassSpecificationUuid);
        BeanUtils.copyProperties(glassSpecificationEntity ,glassSpecificationValue);
        glassSpecificationValue.setTenantUuid(tenantUuid);
        return glassSpecificationValue;
    }

    @Override
    public GlassSpecificationValue deleteGlassSpecification(String tenantUuid, String glassSpecificationUuid) throws Exception {
        GlassSpecificationValue glassSpecificationValue=new GlassSpecificationValue();
        glassSpecificationRepository.softDelete(glassSpecificationUuid);
        GlassSpecificationEntity glassSpecificationEntity =glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid, glassSpecificationUuid);
        BeanUtils.copyProperties(glassSpecificationEntity ,glassSpecificationValue);
        return  glassSpecificationValue;
    }

    @Override
    public List<GlassSpecificationValue> getAllGlassSpecification(String tenantUuid) throws Exception {
        List<GlassSpecificationValue> glassSpecificationValues=new ArrayList<>();
        GlassSpecificationValue glassSpecificationValue=null;
        List<GlassSpecificationEntity> glassSpecificationEntities= glassSpecificationRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i=0; i <glassSpecificationEntities.size(); i++){
            glassSpecificationValue =new GlassSpecificationValue();
            BeanUtils.copyProperties(glassSpecificationEntities.get(i), glassSpecificationValue);
            glassSpecificationValue.setTenantUuid(tenantUuid);
            glassSpecificationValues.add(glassSpecificationValue);
        }

        return glassSpecificationValues;
    }
}
