package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;

import java.util.List;

public interface ProFormaInvoiceService {

    public ProFormaInvoiceValue createProFormInvoice(ProFormaInvoiceValue proFormInvoiceValue) throws Exception;

    public ProFormaInvoiceValue editProFormInvoice(ProFormaInvoiceValue proFormInvoiceValue) throws Exception;

    public ProFormaInvoiceValue getProFormInvoice(String tenantUuid, String proFormInvoiceUuid) throws Exception;

    public int deleteProFormInvoice(String tenantUuid,String proFormInvoiceUuid) throws Exception;

    public List<ProFormaInvoiceValue> getAllProFormInvoice(String tenantUuid) throws Exception;

}
