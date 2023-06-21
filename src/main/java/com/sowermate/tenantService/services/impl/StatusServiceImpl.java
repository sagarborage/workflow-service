package com.sowermate.tenantService.services.impl;


import com.sowermate.tenantService.entities.StatusEntity;
import com.sowermate.tenantService.entities.value.StatusValue;
import com.sowermate.tenantService.repositories.StatusRepository;
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
    @Override
    public StatusValue createStatus(StatusValue statusValue) throws Exception {
        StatusEntity statusEntity=new StatusEntity();
        BeanUtils.copyProperties(statusValue, statusEntity);
        String randomStatusUuid= UUID.randomUUID().toString();
        statusEntity.setStatusUuid(randomStatusUuid);
        BeanUtils.copyProperties(statusRepository.save(statusEntity), statusValue);
        return statusValue;
    }

    @Override
    public StatusValue editStatus(StatusValue statusValue) throws Exception {
        StatusEntity statusEntity = new StatusEntity();
        BeanUtils.copyProperties(statusValue, statusEntity);

        // Check that UUID is not null before searching for the tenant
        if (statusValue.getStatusUuid() != null) {
            StatusEntity matchingStatus = statusRepository.findByStatusUuid(statusValue.getStatusUuid());
            if (matchingStatus !=null) {
                statusEntity.setStatusId(matchingStatus.getStatusId());
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
    public StatusValue getStatus(String statusUuid) throws Exception {
        StatusValue statusValue=new StatusValue();

        StatusEntity statusEntity =statusRepository.findByStatusUuid(statusUuid);
        BeanUtils.copyProperties(statusEntity ,statusValue);
        return statusValue;
    }

    @Override
    public StatusValue deleteStatus(String statusUuid) throws Exception {
        StatusValue statusValue=new StatusValue();
        statusRepository.softDelete(statusUuid);
        StatusEntity  statusEntity =statusRepository.findByStatusUuid(statusUuid) ;
        BeanUtils.copyProperties(statusEntity ,statusValue);
        return  statusValue;
    }

    @Override
    public List<StatusValue> getAllStatus() throws Exception {
        List<StatusValue> statusValues=new ArrayList<>();
        StatusValue statusValue=null;
        List<StatusEntity> statusEntities= statusRepository.findAll();
        for (int i=0; i <statusEntities.size(); i++){
            statusValue =new StatusValue();
            BeanUtils.copyProperties(statusEntities.get(i), statusValue);

            statusValues .add(statusValue);
        }

        return statusValues;
    }
}
