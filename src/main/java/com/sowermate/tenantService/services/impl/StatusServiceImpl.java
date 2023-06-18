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
        String randomStatusId= UUID.randomUUID().toString();
        statusEntity.setUuid(randomStatusId);
        BeanUtils.copyProperties(statusRepository.save(statusEntity), statusValue);
        return statusValue;
    }

    @Override
    public StatusValue editStatus(StatusValue statusValue) throws Exception {
        StatusEntity statusEntity = new StatusEntity();
        BeanUtils.copyProperties(statusValue, statusEntity);

        // Check that UUID is not null before searching for the tenant
        if (statusValue.getUuid() != null) {
            List<StatusEntity> matchingStatus = statusRepository.findByUuid(statusValue.getUuid());
            if (!matchingStatus.isEmpty()) {
                statusEntity.setStatusId(matchingStatus.get(0).getStatusId());
                BeanUtils.copyProperties(statusRepository.save(statusEntity), statusValue);
            } else {
                throw new Exception("No tenant found with UUID " + statusValue.getUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return statusValue;
    }

    @Override
    public StatusValue getStatus(String uuid) throws Exception {
        StatusValue statusValue=new StatusValue();

        StatusEntity statusEntity =statusRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(statusEntity ,statusValue);
        return statusValue;
    }

    @Override
    public StatusValue deleteStatus(String uuid) throws Exception {
        StatusValue statusValue=new StatusValue();
        statusRepository.softDelete(uuid);
        StatusEntity  statusEntity =statusRepository.findByUuid(uuid) .get(0);
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
