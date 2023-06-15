package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;

import java.util.List;

public interface ServiceRateInvoiceService {


    public ServiceRateInvoiceValue createServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue) throws Exception;

    public ServiceRateInvoiceValue editServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue) throws Exception;

    public ServiceRateInvoiceValue getServiceRateInvoice(String uuid) throws Exception;

    public ServiceRateInvoiceValue deleteServiceRateInvoice(String uuid) throws Exception;

    public List<ServiceRateInvoiceValue> getAllServiceRateInvoice() throws Exception;
}
