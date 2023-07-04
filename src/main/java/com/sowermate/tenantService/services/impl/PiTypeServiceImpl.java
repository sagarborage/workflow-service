package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.PiTypeEntity;
import com.sowermate.tenantService.entities.value.PiTypeValue;
import com.sowermate.tenantService.repositories.PiTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.PiTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName = { "Exception" })
public class PiTypeServiceImpl implements PiTypeService {

    @Autowired
    private PiTypeRepository piTypeRepository;

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public PiTypeValue createPiType(PiTypeValue piTypeValue) throws Exception {
        PiTypeEntity piTypeEntity=new PiTypeEntity();
        BeanUtils.copyProperties(piTypeValue, piTypeEntity);
        String randomPiTypeUUid= UUID.randomUUID().toString();
        piTypeEntity.setPiTypeUuid(randomPiTypeUUid);
        piTypeEntity.setTenantEntity(tenantRepository.findByTenantUuid(piTypeValue.getTenantUuid()));
        BeanUtils.copyProperties(piTypeRepository.save(piTypeEntity), piTypeValue);
        return piTypeValue;

    }

    @Override
    public List<PiTypeValue> getAllPiType(String tenantUuid) throws Exception {
        List<PiTypeValue> piTypeValues=new ArrayList<>();
        PiTypeValue piTypeValue=null;
        List<PiTypeEntity> piTypeEntities= piTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i=0; i <piTypeEntities.size(); i++){
            piTypeValue =new PiTypeValue();
            BeanUtils.copyProperties(piTypeEntities.get(i), piTypeValue);
            piTypeValue.setTenantUuid(tenantUuid);
            piTypeValues.add(piTypeValue);
        }

        return piTypeValues;
    }

    @Override
    public PiTypeValue editPiType(PiTypeValue piTypeValue) throws Exception {
        PiTypeEntity piTypeEntity = new PiTypeEntity();
        BeanUtils.copyProperties(piTypeValue, piTypeEntity);

        // Check that UUID is not null before searching for the tenant
        if (piTypeValue.getPiTypeUuid() != null) {
            PiTypeEntity matchingPiType = piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(piTypeValue.getTenantUuid(), piTypeValue.getPiTypeUuid());
            if (matchingPiType!=null) {
                piTypeEntity.setPiTypeId(matchingPiType.getPiTypeId());
                piTypeEntity.setTenantEntity(tenantRepository.findByTenantUuid(piTypeValue.getTenantUuid()));
                BeanUtils.copyProperties(piTypeRepository.save(piTypeEntity), piTypeValue);
            } else {
                throw new Exception("No tenant found with UUID " + piTypeValue.getPiTypeUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return piTypeValue;
    }

    @Override
    public PiTypeValue getPiType(String tenantUuid,String piTypeUuid) throws Exception {
        PiTypeValue piTypeValue=new PiTypeValue();

        PiTypeEntity piTypeEntity =piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(tenantUuid,piTypeUuid);
        piTypeValue.setTenantUuid(tenantUuid);
        BeanUtils.copyProperties(piTypeEntity ,piTypeValue);
        return piTypeValue;
    }


    @Override
    public int deletePiType(String tenantUuid,String piTypeUuid) throws Exception {

      return piTypeRepository.deleteByPiTypeUuid(piTypeUuid) ;

    }
}
