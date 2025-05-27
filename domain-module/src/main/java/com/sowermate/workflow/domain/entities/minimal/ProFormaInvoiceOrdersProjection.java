package com.sowermate.workflow.domain.entities.minimal;

public interface ProFormaInvoiceOrdersProjection {
    String getProformaInvoiceUuid();

    String getPiNumber();

    Long getWorkOrderNo();

    String getPartyBillTo();
}
