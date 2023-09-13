package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;

import java.util.List;

public interface ProFormaInvoiceService {

    public ProFormaInvoiceValue createproFormaInvoice(ProFormaInvoiceValue proFormaInvoiceValue);

    public ProFormaInvoiceValue editproFormaInvoice(ProFormaInvoiceValue proFormaInvoiceValue);

    public ProFormaInvoiceValue getproFormaInvoice(String tenantUuid, String proFormaInvoiceUuid);

    public int deleteproFormaInvoice(String tenantUuid,String proFormaInvoiceUuid);

    public List<ProFormaInvoiceValue> getAllproFormaInvoice(String tenantUuid);

}
