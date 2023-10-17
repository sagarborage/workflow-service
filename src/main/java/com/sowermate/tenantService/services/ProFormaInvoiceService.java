package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceMinimal;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;

import java.time.LocalDateTime;
import java.util.List;

public interface ProFormaInvoiceService {

    public ProFormaInvoiceValue createProFormaInvoice(ProFormaInvoiceValue proFormaInvoiceValue);

    public ProFormaInvoiceValue editProFormaInvoice(ProFormaInvoiceValue proFormaInvoiceValue);

    public ProFormaInvoiceValue getProFormaInvoice(String tenantUuid, String proFormaInvoiceUuid);

    public void deleteProFormaInvoice(String tenantUuid,String proFormaInvoiceUuid);

    //public List<ProFormaInvoiceValue> getAllProFormaInvoice(String tenantUuid);
    public List<ProFormaInvoiceMinimal> getAllProFormaInvoice(String tenantUuid, LocalDateTime startDate, LocalDateTime endDate);

}
