package com.sowermate.report.controllers;

import com.sowermate.report.dtos.InvoiceDto;
import com.sowermate.report.services.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfGenerationController {
    @Autowired
    private PdfGenerationService pdfGenerationService;

    @PostMapping
    public ResponseEntity<String> generateInvoicePdf(@RequestBody InvoiceDto invoiceDto) throws IOException {
        String invoiceUrl = pdfGenerationService.generateInvoice(invoiceDto);
        return new ResponseEntity<>(invoiceUrl, HttpStatus.OK);
    }

}
