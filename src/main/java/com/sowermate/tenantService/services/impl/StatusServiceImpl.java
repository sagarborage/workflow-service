package com.sowermate.tenantService.services.impl;


import com.sowermate.tenantService.entities.StatusEntity;
import com.sowermate.tenantService.entities.value.StatusValue;
import com.sowermate.tenantService.repositories.StatusRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.StatusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName = { "Exception" })
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public StatusValue createStatus(StatusValue statusValue) throws Exception {
        StatusEntity statusEntity=new StatusEntity();
        BeanUtils.copyProperties(statusValue, statusEntity);
        String randomStatusUuid= UUID.randomUUID().toString();
        statusEntity.setStatusUuid(randomStatusUuid);
        statusEntity.setTenantEntity(tenantRepository.findByTenantUuid(statusValue.getTenantUuid()));
        BeanUtils.copyProperties(statusRepository.save(statusEntity), statusValue);
        return statusValue;
    }

    @Override
    public StatusValue editStatus(StatusValue statusValue) throws Exception {
        StatusEntity statusEntity = new StatusEntity();
        BeanUtils.copyProperties(statusValue, statusEntity);

        // Check that UUID is not null before searching for the tenant
        if (statusValue.getStatusUuid() != null) {
            StatusEntity matchingStatus = statusRepository.findByTenantEntity_UuidAndStatusUuid(statusValue.getTenantUuid(),statusValue.getStatusUuid());
            if (matchingStatus !=null) {
                statusEntity.setStatusId(matchingStatus.getStatusId());
                statusEntity.setTenantEntity(tenantRepository.findByTenantUuid(statusValue.getTenantUuid()));
                BeanUtils.copyProperties(statusRepository.save(statusEntity), statusValue);
            } else {
                throw new Exception("No tenant found with UUID " + statusValue.getStatusUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return statusValue;
    }

    @Override
    public StatusValue getStatus(String tenantUuid,String statusUuid) throws Exception {
        StatusValue statusValue=new StatusValue();

        StatusEntity statusEntity =statusRepository.findByTenantEntity_UuidAndStatusUuid(tenantUuid,statusUuid);
        BeanUtils.copyProperties(statusEntity ,statusValue);
        statusValue.setTenantUuid(tenantUuid);
        return statusValue;
    }

    @Override
    public StatusValue deleteStatus(String tenantUuid,String statusUuid) throws Exception {
        StatusValue statusValue=new StatusValue();
        statusRepository.softDelete(statusUuid);
        StatusEntity  statusEntity =statusRepository.findByTenantEntity_UuidAndStatusUuid(tenantUuid,statusUuid) ;
        BeanUtils.copyProperties(statusEntity ,statusValue);
        return  statusValue;
    }

    @Override
    public List<StatusValue> getAllStatus(String tenantUuid) throws Exception {
        List<StatusValue> statusValues=new ArrayList<>();
        StatusValue statusValue=null;
        List<StatusEntity> statusEntities= statusRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i=0; i <statusEntities.size(); i++){
            statusValue =new StatusValue();
            BeanUtils.copyProperties(statusEntities.get(i), statusValue);
            statusValue.setTenantUuid(tenantUuid);
            statusValues .add(statusValue);
        }

        return statusValues;
    }
}
