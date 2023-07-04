package com.sowermate.tenantService.services.impl;
import com.sowermate.tenantService.entities.ServiceRateEntity;
import com.sowermate.tenantService.entities.value.ServiceRateValue;
import com.sowermate.tenantService.repositories.ServiceRateRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.ServiceRateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@Transactional(rollbackForClassName = { "Exception" })
public class ServiceRateServiceImpl implements ServiceRateService {
    @Autowired
    private ServiceRateRepository  serviceRateRepository;

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public ServiceRateValue createServiceRate(ServiceRateValue serviceRateValue) throws Exception {
        ServiceRateEntity serviceRateEntity=new ServiceRateEntity();
        BeanUtils.copyProperties(serviceRateValue, serviceRateEntity);
        String randomTenantUuid= UUID.randomUUID().toString();
        serviceRateEntity.setServiceRateUuid(randomTenantUuid);
        serviceRateEntity.setTenantEntity(tenantRepository.findByTenantUuid(serviceRateValue.getTenantUuid()));
        BeanUtils.copyProperties(serviceRateRepository.save(serviceRateEntity), serviceRateValue);
        return serviceRateValue;
    }

    @Override
    public ServiceRateValue editServiceRate(ServiceRateValue serviceRateValue) throws Exception {
        ServiceRateEntity serviceRateEntity = new ServiceRateEntity();
        BeanUtils.copyProperties(serviceRateValue, serviceRateEntity);

        // Check that UUID is not null before searching for the tenant
        if (serviceRateValue.getServiceRateUuid() != null) {
            ServiceRateEntity matchingServices = serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(serviceRateValue.getTenantUuid(),serviceRateValue.getServiceRateUuid());
            if (matchingServices !=null) {
                serviceRateEntity.setServiceRateId(matchingServices.getServiceRateId());
                serviceRateEntity.setTenantEntity(tenantRepository.findByTenantUuid(serviceRateValue.getTenantUuid()));
                BeanUtils.copyProperties(serviceRateRepository.save(serviceRateEntity), serviceRateValue);
            } else {
                throw new Exception("No tenant found with UUID " + serviceRateValue.getServiceRateUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return serviceRateValue;
    }

    @Override
    public List<ServiceRateValue> getAllServiceRate(String tenantUuid) throws Exception {
            List<ServiceRateValue> serviceRateValues=new ArrayList<>();
            ServiceRateValue serviceRateValue=null;
            List<ServiceRateEntity> serviceRateEntities= serviceRateRepository.findAllByTenantEntity_Uuid(tenantUuid);
            for (int i=0; i <serviceRateEntities.size(); i++){
                serviceRateValue =new ServiceRateValue();
                BeanUtils.copyProperties(serviceRateEntities.get(i), serviceRateValue);
                serviceRateValue.setTenantUuid(tenantUuid);
                serviceRateValues.add(serviceRateValue);
            }

            return serviceRateValues;
        }

    @Override
    public ServiceRateValue getServiceRate(String tenantUuid,String serviceRateUuid) throws Exception {
        ServiceRateValue  serviceRateValue=new ServiceRateValue();

        ServiceRateEntity serviceRateEntity =serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(tenantUuid,serviceRateUuid);
        BeanUtils.copyProperties(serviceRateEntity ,serviceRateValue);
        serviceRateValue.setTenantUuid(tenantUuid);
        return serviceRateValue;
    }
    @Override
    public ServiceRateValue deleteServiceRate(String tenantUuid,String serviceRateUuid) throws Exception {
        ServiceRateValue serviceRateValue=new ServiceRateValue();
        serviceRateRepository.softDelete(serviceRateUuid);
        ServiceRateEntity serviceRateEntity =serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(tenantUuid,serviceRateUuid) ;
        BeanUtils.copyProperties(serviceRateEntity ,serviceRateValue);
        return  serviceRateValue;
    }



}
