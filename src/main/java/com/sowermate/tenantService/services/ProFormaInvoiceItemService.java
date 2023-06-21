package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;

import java.util.List;

public interface ProFormaInvoiceItemService {


    public ProFormaInvoiceItemValue createProFormInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) throws Exception;

    public ProFormaInvoiceItemValue editProFormInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) throws Exception;

    public ProFormaInvoiceItemValue getProFormInvoiceItem(String ProFormaInvoiceItemUuid) throws Exception;

    public ProFormaInvoiceItemValue deleteProFormInvoiceItem(String ProFormaInvoiceItemUuid) throws Exception;

    public List<ProFormaInvoiceItemValue> getAllProFormInvoiceItem() throws Exception;
}
