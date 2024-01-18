package com.sowermate.report.services;



import com.sowermate.report.dtos.InvoiceDto;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;

import java.io.IOException;

public interface PdfGenerationService {
    String generateInvoice( ProFormaInvoiceValue piValue) throws IOException;
}
