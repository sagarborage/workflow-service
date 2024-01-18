package com.sowermate.report.controllers;

import com.sowermate.report.dtos.InvoiceDto;
import com.sowermate.report.services.PdfGenerationService;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/pdf")
public class PdfGenerationController {
    @Autowired
    private PdfGenerationService pdfGenerationService;

    @Autowired
    private ProFormaInvoiceService proFormaInvoiceService;

    @PostMapping("/{tenantUuid}/{proFormaInvoiceUuid}")
    public ResponseEntity<String> generateInvoicePdf(@PathVariable String tenantUuid,
                                                     @PathVariable String proFormaInvoiceUuid) throws IOException {
        ProFormaInvoiceValue proFormaInvoiceValue = proFormaInvoiceService.getProFormaInvoice(tenantUuid, proFormaInvoiceUuid);
        String invoiceUrl = pdfGenerationService.generateInvoice(proFormaInvoiceValue);
        return new ResponseEntity<>(invoiceUrl, HttpStatus.OK);
    }

}
