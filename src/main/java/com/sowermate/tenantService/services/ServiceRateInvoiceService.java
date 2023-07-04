package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;

import java.util.List;

public interface ServiceRateInvoiceService {


    public ServiceRateInvoiceValue createServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue) throws Exception;

    public ServiceRateInvoiceValue editServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue) throws Exception;

    public ServiceRateInvoiceValue getServiceRateInvoice(String tenantUuid,String serviceRateInvoiceUuid) throws Exception;

    public int deleteServiceRateInvoice(String tenantUuid,String serviceRateInvoiceUuid) throws Exception;

    public List<ServiceRateInvoiceValue> getAllServiceRateInvoice(String tenantUuid) throws Exception;
}
