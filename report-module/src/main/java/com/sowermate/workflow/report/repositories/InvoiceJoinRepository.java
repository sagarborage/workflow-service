package com.sowermate.workflow.report.repositories;

import com.sowermate.workflow.report.projections.InvoicePdfProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceJoinRepository {
    String INVOICE_QUERY = "SELECT in.invoiceDate AS invoiceDate, " +
            "in.invoiceNumber AS invoiceNumber, " +
            "in.uuid AS invoiceUuid, " +
            "in.invoiceTotal AS invoiceTotal, " +
            "pa.partyName AS partyName, " +
            "or.orderNumber AS orderNumber, " +
            "or.status AS status, " +
            "or.extraCharges AS extraCharges, " +
            "or.labourCharges AS labourCharges, " +
            "or.subtotalAmount AS subtotalAmount, " +
            "or.offerDiscountAmount AS offerDiscountAmount, " +
            "or.samDiscountAmount AS samDiscountAmount, " +
            "or.discountAmount AS discountAmount, " +
            "or.tcsAmount AS tcsAmount, " +
            "or.totalTaxAmount AS totalTaxAmount, " +
            "or.totalGstAmount AS totalGstAmount, " +
            "or.totalAmount AS totalAmount " +
            "FROM Order or " +
            "JOIN Party pa ON or.sellerId = pa.id " +
            "JOIN Invoice in ON in.orderId = or.id " +
            "where in.invoiceNumber = :invoiceNumber ";

    @Query(INVOICE_QUERY)
    InvoicePdfProjection getInvoice(@Param("invoiceNumber") String invoiceNumber);
}
