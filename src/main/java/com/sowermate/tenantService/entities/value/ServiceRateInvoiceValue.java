package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class ServiceRateInvoiceValue {

    private int serviceRateInvoiceId;
    private String uuid;
    private int quantity;
    private int rate;
    private int total;
    private int proFormaInvoiceId;
    private int serviceRateId;
}
