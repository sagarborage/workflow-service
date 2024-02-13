package com.sowermate.report.services;



import com.sowermate.report.dtos.PIReportAddressDto;
import com.sowermate.report.dtos.PIReportDetails;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;

import java.io.IOException;

public interface PdfGenerationService {
    String generateInvoice(ProFormaInvoiceValue piValue, PIReportDetails reportDetails) throws IOException;
}
