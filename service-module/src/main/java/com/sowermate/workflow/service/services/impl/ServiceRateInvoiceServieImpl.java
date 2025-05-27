package com.sowermate.workflow.service.services.impl;


import com.sowermate.workflow.domain.entities.ProFormaInvoiceEntity;
import com.sowermate.workflow.domain.entities.ServiceRateEntity;
import com.sowermate.workflow.domain.entities.ServiceRateInvoiceEntity;
import com.sowermate.workflow.domain.entities.value.ServiceRateInvoiceValue;
import com.sowermate.workflow.persistence.repositories.ProFormaInvoiceRepository;
import com.sowermate.workflow.persistence.repositories.ServiceRateInvoiceRepository;
import com.sowermate.workflow.persistence.repositories.ServiceRateRepository;
import com.sowermate.workflow.service.services.ServiceRateInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ServiceRateInvoiceServieImpl implements ServiceRateInvoiceService {

    @Autowired
    private ServiceRateInvoiceRepository serviceRateInvoiceRepository;

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    private ServiceRateRepository serviceRateRepository;

    @Override
    public ServiceRateInvoiceValue createServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue) {
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByUuid(serviceRateInvoiceValue.getProFormaInvoiceUuid());
        ServiceRateEntity serviceRateEntity = serviceRateRepository.findByUuid(serviceRateInvoiceValue.getServiceRateUuid());
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = serviceRateInvoiceValue.toEntity().toBuilder()
                .proFormaInvoiceEntity(proFormaInvoiceEntity)
                .serviceRateEntity(serviceRateEntity)
                .build();
        return serviceRateInvoiceRepository.save(serviceRateInvoiceEntity).toDTO();
    }

    @Override
    public ServiceRateInvoiceValue editServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue) {
        ServiceRateEntity serviceRateEntity = serviceRateRepository.findByUuid(serviceRateInvoiceValue.getServiceRateUuid());
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByUuid(serviceRateInvoiceValue.getProFormaInvoiceUuid());
        ServiceRateInvoiceEntity tempServiceRateInvoiceEntity = serviceRateInvoiceRepository.findByProFormaInvoiceUuidUuidAndServiceRateInvoiceUuid(serviceRateInvoiceValue.getProFormaInvoiceUuid(), serviceRateInvoiceValue.getUuid());
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = serviceRateInvoiceValue.toEntity().toBuilder()
                .id(tempServiceRateInvoiceEntity.getId())
                .serviceRateEntity(serviceRateEntity)
                .proFormaInvoiceEntity(proFormaInvoiceEntity)
                .createdDateTime(tempServiceRateInvoiceEntity.getCreatedDateTime())
                .createdBy(tempServiceRateInvoiceEntity.getCreatedBy())
                .build();

        return serviceRateInvoiceRepository.save(serviceRateInvoiceEntity).toDTO();


//        TenantEntity tenantEntity = tenantRepository.findByUuid(serviceRateValue.getTenantUuid());
//        ServiceRateEntity tempServiceRateEntity = serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(serviceRateValue.getTenantUuid(), serviceRateValue.getServiceRateUuid());
//
//        ServiceRateEntity serviceRateEntity = serviceRateValue.toEntity().toBuilder()
//                .id(tempServiceRateEntity.getId())
//                .tenantEntity(tenantEntity)
//                .createdDateTime(tempServiceRateEntity.getCreatedDateTime())
//                .createdBy(tempServiceRateEntity.getCreatedBy())
//                .build();
    }

    @Override
    public ServiceRateInvoiceValue getServiceRateInvoice(String proFormaInvoiceUuid, String serviceRateInvoiceUuid) {
        return serviceRateInvoiceRepository.findByProFormaInvoiceUuidUuidAndServiceRateInvoiceUuid(proFormaInvoiceUuid, serviceRateInvoiceUuid).toDTO();
    }

    @Override
    public int deleteServiceRateInvoice(String tenantUuid, String serviceRateInvoiceUuid) {
        return serviceRateInvoiceRepository.deleteByUuid(serviceRateInvoiceUuid);
    }

    @Override
    public List<ServiceRateInvoiceValue> getAllServiceRateInvoice(String proFormaInvoiceUuid) {
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByUuid(proFormaInvoiceUuid);
        List<ServiceRateInvoiceEntity> serviceRateInvoiceEntities = serviceRateInvoiceRepository.findAllByProFormaInvoiceEntity_Id(proFormaInvoiceEntity.getId());
        return serviceRateInvoiceEntities.stream().map(sri -> sri.toDTO()).collect(Collectors.toList());
    }
}