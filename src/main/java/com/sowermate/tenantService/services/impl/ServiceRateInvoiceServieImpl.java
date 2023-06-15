package com.sowermate.tenantService.services.impl;


import com.sowermate.tenantService.entities.ServiceRateInvoiceEntity;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.repositories.ServiceRateInvoiceRepository;
import com.sowermate.tenantService.repositories.ServiceRateRepository;
import com.sowermate.tenantService.services.ServiceRateInvoiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public ServiceRateInvoiceValue createServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue) throws Exception {
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = new ServiceRateInvoiceEntity();
        BeanUtils.copyProperties(serviceRateInvoiceValue, serviceRateInvoiceEntity);
        String randomServiceRateInvoiceId = UUID.randomUUID().toString();
        serviceRateInvoiceEntity.setUuid(randomServiceRateInvoiceId);
        serviceRateInvoiceEntity.setProFormaInvoiceEntities(proFormaInvoiceRepository.findByProFormaInvoiceId(serviceRateInvoiceValue.getProFormaInvoiceId()).get(0));
        serviceRateInvoiceEntity.setServiceRateEntity(serviceRateRepository.findByServiceRateId(serviceRateInvoiceValue.getServiceRateId()).get(0));
        BeanUtils.copyProperties(serviceRateInvoiceRepository.save(serviceRateInvoiceEntity), serviceRateInvoiceValue);
        return serviceRateInvoiceValue;
    }

    @Override
    public ServiceRateInvoiceValue editServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue) throws Exception {
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = new ServiceRateInvoiceEntity();
        BeanUtils.copyProperties(serviceRateInvoiceValue, serviceRateInvoiceEntity);
        serviceRateInvoiceEntity.setProFormaInvoiceEntities(proFormaInvoiceRepository.findByProFormaInvoiceId(serviceRateInvoiceValue.getProFormaInvoiceId()).get(0));
        serviceRateInvoiceEntity.setServiceRateEntity(serviceRateRepository.findByServiceRateId(serviceRateInvoiceValue.getServiceRateId()).get(0));
        serviceRateInvoiceEntity.setServiceRateInvoiceId(serviceRateInvoiceRepository.findByUuid(serviceRateInvoiceValue.getUuid()).get(0).getServiceRateInvoiceId());
        BeanUtils.copyProperties(serviceRateInvoiceRepository.save(serviceRateInvoiceEntity), serviceRateInvoiceValue);
        return serviceRateInvoiceValue;
    }

    @Override
    public ServiceRateInvoiceValue getServiceRateInvoice(String uuid) throws Exception {
        ServiceRateInvoiceValue serviceRateInvoiceValue = new ServiceRateInvoiceValue();
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = serviceRateInvoiceRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(serviceRateInvoiceEntity, serviceRateInvoiceValue);
        return serviceRateInvoiceValue;
    }

    @Override
    public ServiceRateInvoiceValue deleteServiceRateInvoice(String uuid) throws Exception {
        ServiceRateInvoiceValue serviceRateInvoiceValue = new ServiceRateInvoiceValue();
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = serviceRateInvoiceRepository.deleteByUuid(uuid).get(0);
        BeanUtils.copyProperties(serviceRateInvoiceEntity, serviceRateInvoiceValue);
        return serviceRateInvoiceValue;
    }

    @Override
    public List<ServiceRateInvoiceValue> getAllServiceRateInvoice() throws Exception {
        List<ServiceRateInvoiceValue> serviceRateInvoiceValues = new ArrayList<>();
        ServiceRateInvoiceValue serviceRateInvoiceValue = null;
        List<ServiceRateInvoiceEntity> serviceRateInvoiceEntities = serviceRateInvoiceRepository.findAll();
        for (int i = 0; i < serviceRateInvoiceEntities.size(); i++) {
            serviceRateInvoiceValue = new ServiceRateInvoiceValue();
            BeanUtils.copyProperties(serviceRateInvoiceEntities.get(i), serviceRateInvoiceValue);
            serviceRateInvoiceValues.add(serviceRateInvoiceValue);
        }
        return serviceRateInvoiceValues;
    }
}