package com.sowermate.report.controllers;

import com.sowermate.report.dtos.PIReportAddressDto;
import com.sowermate.report.dtos.PIReportDetails;
import com.sowermate.report.services.PdfGenerationService;
import com.sowermate.tenantService.entities.minimal.CompanyInfoProjection;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.services.CompanyService;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfGenerationController {
    @Autowired
    private PdfGenerationService pdfGenerationService;

    @Autowired
    private ProFormaInvoiceService proFormaInvoiceService;

    @Autowired
    private CompanyService companyService;

    @PostMapping("/{tenantUuid}/{proFormaInvoiceUuid}")
    public ResponseEntity<String> generateInvoicePdf(@PathVariable String tenantUuid,
                                                     @PathVariable String proFormaInvoiceUuid) throws IOException {
        ProFormaInvoiceValue proFormaInvoiceValue = proFormaInvoiceService.getProFormaInvoice(tenantUuid, proFormaInvoiceUuid);
        CompanyInfoProjection billTo = companyService.getCompanyInfo(proFormaInvoiceValue.getTenantUuid(), proFormaInvoiceValue.getPartyBillToUuid());
        CompanyInfoProjection shipTo = companyService.getCompanyInfo(proFormaInvoiceValue.getTenantUuid(), proFormaInvoiceValue.getPartyShipToUuid());
        PIReportDetails reportDetails =new PIReportDetails();
        reportDetails.setBillTo(billTo);
        reportDetails.setShipTo(shipTo);
        String invoiceUrl = pdfGenerationService.generateInvoice(proFormaInvoiceValue, reportDetails);
        return new ResponseEntity<>(invoiceUrl, HttpStatus.OK);
    }

}
