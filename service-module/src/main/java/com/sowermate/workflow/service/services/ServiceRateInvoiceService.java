package com.sowermate.workflow.service.services;

import com.sowermate.workflow.domain.entities.value.ServiceRateInvoiceValue;

import java.util.List;

public interface ServiceRateInvoiceService {
    public ServiceRateInvoiceValue createServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue);

    public ServiceRateInvoiceValue editServiceRateInvoice(ServiceRateInvoiceValue serviceRateInvoiceValue);

    public ServiceRateInvoiceValue getServiceRateInvoice(String tenantUuid, String serviceRateInvoiceUuid);

    public int deleteServiceRateInvoice(String tenantUuid, String serviceRateInvoiceUuid);

    public List<ServiceRateInvoiceValue> getAllServiceRateInvoice(String proFormaInvoiceUuid);
}
