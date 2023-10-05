package com.sowermate.tenantService.services.impl;


import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.ServiceRateEntity;
import com.sowermate.tenantService.entities.ServiceRateInvoiceEntity;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.repositories.ServiceRateInvoiceRepository;
import com.sowermate.tenantService.repositories.ServiceRateRepository;
import com.sowermate.tenantService.services.ServiceRateInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        return serviceRateInvoiceValue;
    }

    @Override
    public ServiceRateInvoiceValue getServiceRateInvoice(String proFormaInvoiceUuid,String serviceRateInvoiceUuid) {
        return serviceRateInvoiceRepository.findByProFormaInvoiceUuidUuidAndServiceRateInvoiceUuid(proFormaInvoiceUuid, serviceRateInvoiceUuid).toDTO();
    }

    @Override
    public int deleteServiceRateInvoice(String tenantUuid,String serviceRateInvoiceUuid) {
        return serviceRateInvoiceRepository.deleteByUuid(serviceRateInvoiceUuid);
    }

    @Override
    public List<ServiceRateInvoiceValue> getAllServiceRateInvoice(String proFormaInvoiceUuid) {
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByUuid(proFormaInvoiceUuid);
        List<ServiceRateInvoiceEntity> serviceRateInvoiceEntities = serviceRateInvoiceRepository.findAllByProFormaInvoiceEntity_Id(proFormaInvoiceEntity.getId());
        return serviceRateInvoiceEntities.stream().map(sri -> sri.toDTO()).collect(Collectors.toList());
    }
}