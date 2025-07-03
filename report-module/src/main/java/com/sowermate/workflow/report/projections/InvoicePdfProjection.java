package com.sowermate.workflow.report.projections;

import java.sql.Date;

public interface InvoicePdfProjection {

    String getInvoiceUuid();

    Date getInvoiceDate();

    String getInvoiceNumber();

    Integer getInvoiceTotal();

    String getPartyName();

    String getOrderNumber();

    String getStatus();

    Double getExtraCharges();

    Double getLabourCharges();

    Double getSubTotalAmount();

    Double getOfferDiscountAmount();

    Double getSamDiscountAmount();

    Double getDiscountAmount();

    Double getTcsAmount();

    Double getTotalTaxAmount();

    Double getTotalGstAmount();

    Double getTotalAmount();
}
