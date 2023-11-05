package com.sowermate.tenantService.entities.minimal;

public interface ProFormaInvoiceOrdersProjection {
    String getProformaInvoiceUuid();

    String getPiNumber();

    Long getWorkOrderNo();

    Long getPartyBillTo();
}
