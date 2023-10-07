package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;

import java.util.List;

public interface ProFormaInvoiceItemService {


    public ProFormaInvoiceItemValue createproFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue);

    public ProFormaInvoiceItemValue editproFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue);

    public ProFormaInvoiceItemValue getproFormaInvoiceItem(String tenantUuid,String proFormaInvoiceItemUuid);

    public int deleteproFormaInvoiceItem(String tenantUuid,String proFormaInvoiceItemUuid);

    public List<ProFormaInvoiceItemValue> getAllproFormaInvoiceItem(String tenantUuid);
}
