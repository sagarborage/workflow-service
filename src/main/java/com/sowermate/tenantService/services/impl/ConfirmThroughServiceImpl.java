package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import com.sowermate.tenantService.repositories.ConfirmThroughRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.ConfirmThroughService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName = { "Exception" })
public class ConfirmThroughServiceImpl implements ConfirmThroughService {

    @Autowired
    private ConfirmThroughRepository confirmThroughRepository;

    @Autowired
    TenantRepository tenantRepository;
    @Override
    public ConfirmThroughValue createConfirmThrough(ConfirmThroughValue confirmThroughValue) throws Exception {
        ConfirmThroughEntity confirmThroughEntity=new ConfirmThroughEntity();
        BeanUtils.copyProperties(confirmThroughValue, confirmThroughEntity);
        String randomConfirmThroughUuid= UUID.randomUUID().toString();
        confirmThroughEntity.setConfirmThroughUuid(randomConfirmThroughUuid);
        confirmThroughEntity.setTenantEntity(tenantRepository.findByTenantUuid(confirmThroughValue.getTenantUuid()));
        BeanUtils.copyProperties(confirmThroughRepository.save(confirmThroughEntity), confirmThroughValue);
        return confirmThroughValue;
    }

    @Override
    public List<ConfirmThroughValue> getAllConfirmThrough(String tenantUuid) throws Exception {
        List<ConfirmThroughValue> confirmThroughValues=new ArrayList<>();
        ConfirmThroughValue confirmThroughValue=null;
        List<ConfirmThroughEntity> confirmThroughEntities= confirmThroughRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i=0; i <confirmThroughEntities.size(); i++){
            confirmThroughValue =new ConfirmThroughValue();
            BeanUtils.copyProperties(confirmThroughEntities.get(i), confirmThroughValue);
            confirmThroughValue.setTenantUuid(tenantUuid);
            confirmThroughValues.add(confirmThroughValue);
        }

        return confirmThroughValues;
    }

    @Override
    public ConfirmThroughValue editConfirmThrough(ConfirmThroughValue confirmThroughValue) throws Exception {
        ConfirmThroughEntity confirmThroughEntity = new ConfirmThroughEntity();
        BeanUtils.copyProperties(confirmThroughValue, confirmThroughEntity);

        // Check that UUID is not null before searching for the tenant
        if (confirmThroughValue.getConfirmThroughUuid() != null) {
            ConfirmThroughEntity matchingConfirmThrough = confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(confirmThroughValue.getTenantUuid(),confirmThroughValue.getConfirmThroughUuid());
            if (matchingConfirmThrough !=null) {
                confirmThroughEntity.setConfirmThroughId(matchingConfirmThrough.getConfirmThroughId());
                confirmThroughEntity.setTenantEntity(tenantRepository.findByTenantUuid(confirmThroughValue.getTenantUuid()));
                BeanUtils.copyProperties(confirmThroughRepository.save(confirmThroughEntity), confirmThroughValue);
            } else {
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return confirmThroughValue;
    }

    @Override
    public ConfirmThroughValue getConfirmThrough(String tenantUuid, String confirmThroughUuid) throws Exception {
        ConfirmThroughValue confirmThroughValue=new ConfirmThroughValue();
        ConfirmThroughEntity confirmThroughEntity =confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUuid,confirmThroughUuid);
        BeanUtils.copyProperties(confirmThroughEntity ,confirmThroughValue);
        confirmThroughValue.setTenantUuid(tenantUuid);
        return confirmThroughValue;
    }

    @Override
    public int deleteConfirmThrough( String tenantUuid,  String confirmThroughUuid) throws Exception {

       return confirmThroughRepository.deleteByConfirmThroughUuid( confirmThroughUuid) ;

    }
}
