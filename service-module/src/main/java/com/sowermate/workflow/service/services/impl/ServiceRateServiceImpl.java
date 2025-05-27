package com.sowermate.workflow.service.services.impl;

import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.core.tenant.repositories.TenantRepository;
import com.sowermate.workflow.domain.entities.ServiceRateEntity;
import com.sowermate.workflow.domain.entities.value.ServiceRateValue;
import com.sowermate.workflow.persistence.repositories.ServiceRateRepository;
import com.sowermate.workflow.service.services.ServiceRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ServiceRateServiceImpl implements ServiceRateService {
    @Autowired
    private ServiceRateRepository serviceRateRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public ServiceRateValue createServiceRate(ServiceRateValue serviceRateValue) {
        Tenant tenantEntity = tenantRepository.findByUuid(serviceRateValue.getTenantUuid());
        ServiceRateEntity serviceRateEntity = serviceRateValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .build();
        return serviceRateRepository.save(serviceRateEntity).toDTO().toBuilder().tenantUuid(tenantEntity.getUuid()).build();
    }

    @Override
    public ServiceRateValue editServiceRate(ServiceRateValue serviceRateValue) {
        Tenant tenantEntity = tenantRepository.findByUuid(serviceRateValue.getTenantUuid());
        ServiceRateEntity tempServiceRateEntity = serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(serviceRateValue.getTenantUuid(), serviceRateValue.getUuid());

        ServiceRateEntity serviceRateEntity = serviceRateValue.toEntity().toBuilder()
                .id(tempServiceRateEntity.getId())
                .tenantEntity(tenantEntity)
                .createdDateTime(tempServiceRateEntity.getCreatedDateTime())
                .createdBy(tempServiceRateEntity.getCreatedBy())
                .version(tempServiceRateEntity.getVersion())
                .build();
        return serviceRateRepository.save(serviceRateEntity).toDTO().toBuilder().tenantUuid(tenantEntity.getUuid()).build();
    }

    @Override
    public List<ServiceRateValue> getAllServiceRate(String tenantUuid) {
        List<ServiceRateEntity> serviceRateEntities = serviceRateRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return serviceRateEntities.stream().map(sre -> sre.toDTO()).collect(Collectors.toList());
    }

    @Override
    public ServiceRateValue getServiceRate(String tenantUuid, String serviceRateUuid) {
        ServiceRateEntity tempServiceRateEntity = serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(tenantUuid, serviceRateUuid);
        return tempServiceRateEntity.toDTO();
    }

    @Override
    public ServiceRateValue deleteServiceRate(String tenantUuid, String serviceRateUuid) {
        serviceRateRepository.softDelete(serviceRateUuid);
        ServiceRateEntity serviceRateEntity = serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(tenantUuid, serviceRateUuid);
        return serviceRateEntity.toDTO();
    }
}
