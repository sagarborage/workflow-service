package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.PiTypeEntity;
import com.sowermate.tenantService.entities.value.PiTypeValue;
import com.sowermate.tenantService.repositories.PiTypeRepository;
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
    @Override
    public PiTypeValue CreatePiType(PiTypeValue piTypeValue) throws Exception {
        PiTypeEntity piTypeEntity=new PiTypeEntity();
        BeanUtils.copyProperties(piTypeValue, piTypeEntity);
        String randomPiTypeId= UUID.randomUUID().toString();
        piTypeEntity.setUuid(randomPiTypeId);
        BeanUtils.copyProperties(piTypeRepository.save(piTypeEntity), piTypeValue);
        return piTypeValue;

    }

    @Override
    public List<PiTypeValue> getAllPiType() throws Exception {
        List<PiTypeValue> piTypeValues=new ArrayList<>();
        PiTypeValue piTypeValue=null;
        List<PiTypeEntity> piTypeEntities= piTypeRepository.findAll();
        for (int i=0; i <piTypeEntities.size(); i++){
            piTypeValue =new PiTypeValue();
            BeanUtils.copyProperties(piTypeEntities.get(i), piTypeValue);

            piTypeValues.add(piTypeValue);
        }

        return piTypeValues;
    }

    @Override
    public PiTypeValue editPiType(PiTypeValue piTypeValue) throws Exception {
        PiTypeEntity piTypeEntity = new PiTypeEntity();
        BeanUtils.copyProperties(piTypeValue, piTypeEntity);

        // Check that UUID is not null before searching for the tenant
        if (piTypeValue.getUuid() != null) {
            List<PiTypeEntity> matchingPiType = piTypeRepository.findByUuid(piTypeValue.getUuid());
            if (!matchingPiType.isEmpty()) {
                piTypeEntity.setPiTypeId(matchingPiType.get(0).getPiTypeId());
                BeanUtils.copyProperties(piTypeRepository.save(piTypeEntity), piTypeValue);
            } else {
                throw new Exception("No tenant found with UUID " + piTypeValue.getUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return piTypeValue;
    }

    @Override
    public PiTypeValue getPiType(String uuid) throws Exception {
        PiTypeValue piTypeValue=new PiTypeValue();

        PiTypeEntity piTypeEntity =piTypeRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(piTypeEntity ,piTypeValue);
        return piTypeValue;
    }


    @Override
    public PiTypeValue deletePiType(String uuid) throws Exception {
        PiTypeValue piTypeValue=new PiTypeValue();
        PiTypeEntity piTypeEntity =piTypeRepository.deletePiTypeByUuid(uuid) .get(0);
        BeanUtils.copyProperties(piTypeEntity ,piTypeValue);
        return  piTypeValue;

    }
}
