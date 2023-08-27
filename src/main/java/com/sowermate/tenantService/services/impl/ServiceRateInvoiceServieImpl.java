package com.sowermate.tenantService.services.impl;


import com.sowermate.tenantService.entities.ServiceRateInvoiceEntity;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.repositories.ServiceRateInvoiceRepository;
import com.sowermate.tenantService.repositories.ServiceRateRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.ServiceRateInvoiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName= {"Exception"})
public class ServiceRateInvoiceServieImpl implements ServiceRateInvoiceService {

    @Autowired
    private ServiceRateInvoiceRepository serviceRateInvoiceRepository;

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    private ServiceRateRepository serviceRateRepository;

    @Autowired
    private TenantRepository  tenantRepository;

    @Override
    public ServiceRateInvoiceValue createServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue) throws Exception {

        String tenantUuid = serviceRateInvoiceValue.getTenantValue().getUuid();
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = serviceRateInvoiceValue.toEntity().toBuilder()
                .serviceRateInvoiceUuid(CommonUtils.generateUUID())
                .tenantEntity(tenantRepository.findByTenantUuid(tenantUuid))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(tenantUuid,
                        serviceRateInvoiceValue.getProFormaInvoice().getProFormInvoiceUuid()))
                .serviceRateEntity(serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(tenantUuid, serviceRateInvoiceValue.getServiceRate().getServiceRateUuid()))
                .build();
        return serviceRateInvoiceEntity.toDTO();
/*        ServiceRateInvoiceEntity serviceRateInvoiceEntity = new ServiceRateInvoiceEntity();
        BeanUtils.copyProperties(serviceRateInvoiceValue, serviceRateInvoiceEntity);
        serviceRateInvoiceEntity.setServiceRateInvoiceUuid(CommonUtils.generateUUID());
        serviceRateInvoiceEntity.setTenantEntity(tenantRepository.findByTenantUuid(serviceRateInvoiceValue.getTenantUuid()));
        serviceRateInvoiceEntity.setProFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(serviceRateInvoiceValue.getTenantUuid(),serviceRateInvoiceValue.getProFormaInvoiceUuid()));
        serviceRateInvoiceEntity.setServiceRateEntity(serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(serviceRateInvoiceValue.getTenantUuid(),serviceRateInvoiceValue.getServiceRateUuid()));
        BeanUtils.copyProperties(serviceRateInvoiceRepository.save(serviceRateInvoiceEntity), serviceRateInvoiceValue);
        return serviceRateInvoiceValue;*/
    }

    @Override
    public ServiceRateInvoiceValue editServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue) throws Exception {

        String tenantUuid = serviceRateInvoiceValue.getTenantValue().getUuid();
        ServiceRateInvoiceEntity tempServiceRateInvoiceEntity = serviceRateInvoiceRepository.findByTenantEntity_UuidAndServiceRateInvoiceUuid(tenantUuid,
                serviceRateInvoiceValue.getServiceRateInvoiceUuid());
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = serviceRateInvoiceValue.toEntity().toBuilder()
                .serviceRateInvoiceId(tempServiceRateInvoiceEntity.getServiceRateInvoiceId())
                .tenantEntity(tenantRepository.findByTenantUuid(tenantUuid))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(tenantUuid,
                        serviceRateInvoiceValue.getProFormaInvoice().getProFormInvoiceUuid()))
                .serviceRateEntity(serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(tenantUuid, serviceRateInvoiceValue.getServiceRate().getServiceRateUuid()))
                .build();
        return serviceRateInvoiceEntity.toDTO();
/*        ServiceRateInvoiceEntity serviceRateInvoiceEntity = new ServiceRateInvoiceEntity();
        BeanUtils.copyProperties(serviceRateInvoiceValue, serviceRateInvoiceEntity);
        serviceRateInvoiceEntity.setTenantEntity(tenantRepository.findByTenantUuid(serviceRateInvoiceValue.getTenantUuid()));
        serviceRateInvoiceEntity.setProFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(serviceRateInvoiceValue.getTenantUuid(),serviceRateInvoiceValue.getProFormaInvoiceUuid()));
        serviceRateInvoiceEntity.setServiceRateEntity(serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(serviceRateInvoiceValue.getTenantUuid(),serviceRateInvoiceValue.getServiceRateUuid()));
        serviceRateInvoiceEntity.setServiceRateInvoiceId(serviceRateInvoiceRepository.findByTenantEntity_UuidAndServiceRateInvoiceUuid(serviceRateInvoiceValue.getTenantUuid(),serviceRateInvoiceValue.getServiceRateInvoiceUuid()).getServiceRateInvoiceId());
        BeanUtils.copyProperties(serviceRateInvoiceRepository.save(serviceRateInvoiceEntity), serviceRateInvoiceValue);
        return serviceRateInvoiceValue;*/
    }

    @Override
    public ServiceRateInvoiceValue getServiceRateInvoice(String tenantUuid,String serviceRateInvoiceUuid) throws Exception {
/*        ServiceRateInvoiceValue serviceRateInvoiceValue = new ServiceRateInvoiceValue();
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = serviceRateInvoiceRepository.findByTenantEntity_UuidAndServiceRateInvoiceUuid(tenantUuid,serviceRateInvoiceUuid);
        BeanUtils.copyProperties(serviceRateInvoiceEntity, serviceRateInvoiceValue);*/
        return serviceRateInvoiceRepository.findByTenantEntity_UuidAndServiceRateInvoiceUuid(tenantUuid, serviceRateInvoiceUuid).toDTO();
    }

    @Override
    public int deleteServiceRateInvoice(String tenantUuid,String serviceRateInvoiceUuid) throws Exception {
        return serviceRateInvoiceRepository.deleteByServiceRateInvoiceUuid(serviceRateInvoiceUuid);
    }

    @Override

    public List<ServiceRateInvoiceValue> getAllServiceRateInvoice(String tenantUuid) {
        List<ServiceRateInvoiceEntity> serviceRateInvoiceEntities = serviceRateInvoiceRepository.findAllByTenantEntity_Uuid(tenantUuid);
/*        List<ServiceRateInvoiceValue> serviceRateInvoiceValues = new ArrayList<>();
        for (ServiceRateInvoiceEntity entity : serviceRateInvoiceEntities) {
            ServiceRateInvoiceValue value = new ServiceRateInvoiceValue();
            BeanUtils.copyProperties(entity, value);
            serviceRateInvoiceValues.add(value);
        }*/
        return serviceRateInvoiceEntities.stream().map(sri -> sri.toDTO()).collect(Collectors.toList());
    }
}