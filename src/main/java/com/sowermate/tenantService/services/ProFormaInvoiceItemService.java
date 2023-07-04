package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;

import java.util.List;

public interface ProFormaInvoiceItemService {


    public ProFormaInvoiceItemValue createProFormInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) throws Exception;

    public ProFormaInvoiceItemValue editProFormInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) throws Exception;

    public ProFormaInvoiceItemValue getProFormInvoiceItem(String tenantUuid,String proFormaInvoiceItemUuid) throws Exception;

    public int deleteProFormInvoiceItem(String tenantUuid,String proFormaInvoiceItemUuid) throws Exception;

    public List<ProFormaInvoiceItemValue> getAllProFormInvoiceItem(String tenantUuid) throws Exception;
}
