package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class ServiceRateInvoiceValue {
    private String proFormaInvoiceUuid;
    private String  serviceRateInvoiceUuid;
    private String serviceRateUuid;
    private int quantity;
    private int rate;
    private int total;
}
