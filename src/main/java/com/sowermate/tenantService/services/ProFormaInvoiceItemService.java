package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;

import java.util.List;

public interface ProFormaInvoiceItemService {


    public ProFormaInvoiceItemValue createProFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue);

    public ProFormaInvoiceItemValue editProFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue);

    public ProFormaInvoiceItemValue getProFormaInvoiceItem(String tenantUuid, String proFormaInvoiceItemUuid);

    public int deleteProFormaInvoiceItem(String tenantUuid, String proFormaInvoiceItemUuid);

    public List<ProFormaInvoiceItemValue> getAllProFormaInvoiceItem(String tenantUuid);
}
