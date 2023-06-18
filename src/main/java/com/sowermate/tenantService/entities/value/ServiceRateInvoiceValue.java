package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class ServiceRateInvoiceValue {
    private String proFormaInvoiceUUID;
    private String uuid;
    private String serviceRateUUID;
    private int quantity;
    private int rate;
    private int total;
}
