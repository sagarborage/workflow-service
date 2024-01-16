package com.sowermate.report.services;



import com.sowermate.report.dtos.InvoiceDto;

import java.io.IOException;

public interface PdfGenerationService {
    String generateInvoice( InvoiceDto invoiceDto) throws IOException;
}
