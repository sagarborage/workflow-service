package com.sowermate.report.services;



import com.sowermate.report.dtos.PIReportDetails;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;

import java.io.IOException;

public interface PdfGenerationService {
    byte[] generateProformaInvoice(ProFormaInvoiceValue piValue, PIReportDetails reportDetails) throws IOException;
    byte[] generateWorkOrder(ProFormaInvoiceValue piValue, PIReportDetails reportDetails) throws IOException;
}
