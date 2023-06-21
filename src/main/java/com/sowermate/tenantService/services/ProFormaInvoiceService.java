package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ProFormInvoiceValue;

import java.util.List;

public interface ProFormaInvoiceService {

    public ProFormInvoiceValue createProFormInvoice(ProFormInvoiceValue proFormInvoiceValue) throws Exception;

    public ProFormInvoiceValue editProFormInvoice(ProFormInvoiceValue proFormInvoiceValue) throws Exception;

    public ProFormInvoiceValue getProFormInvoice(String proFormInvoiceUuid) throws Exception;

    public ProFormInvoiceValue deleteProFormInvoice(String proFormInvoiceUuid) throws Exception;

    public List<ProFormInvoiceValue> getAllProFormInvoice() throws Exception;

}
