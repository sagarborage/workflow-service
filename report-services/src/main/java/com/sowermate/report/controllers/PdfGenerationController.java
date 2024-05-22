package com.sowermate.report.controllers;

import com.sowermate.image.services.PdfService;
import com.sowermate.report.dtos.*;
import com.sowermate.report.services.PdfGenerationService;
import com.sowermate.tenantService.entities.minimal.CompanyInfoProjection;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.services.CompanyService;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/pdf")
public class PdfGenerationController {
    @Autowired
    private PdfGenerationService pdfGenerationService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ProFormaInvoiceService proFormaInvoiceService;

    @Autowired
    private CompanyService companyService;

    @PostMapping("/{tenantUuid}/{proFormaInvoiceUuid}")
    public ResponseEntity<byte[]> generateInvoicePdf(@PathVariable String tenantUuid,
                                                     @PathVariable String proFormaInvoiceUuid) throws IOException {
        ProFormaInvoiceValue proFormaInvoiceValue = proFormaInvoiceService.getProFormaInvoice(tenantUuid, proFormaInvoiceUuid);
        CompanyInfoProjection billTo = companyService.getCompanyInfo(proFormaInvoiceValue.getTenantUuid(), proFormaInvoiceValue.getPartyBillToUuid());
        CompanyInfoProjection shipTo = companyService.getCompanyInfo(proFormaInvoiceValue.getTenantUuid(), proFormaInvoiceValue.getPartyShipToUuid());
        PIReportDetails reportDetails = new PIReportDetails();
        reportDetails.setBillTo(billTo);
        reportDetails.setShipTo(shipTo);
        List<String> piItemsPdfUrls = new ArrayList<>();
        proFormaInvoiceValue.getProFormaInvoiceItems().stream().peek(ProFormaInvoiceItemValue -> {
            if (ProFormaInvoiceItemValue.getFileUrl() != null) {
                if (!ProFormaInvoiceItemValue.getFileUrl().equals("Error handling the PDF.")) {
                    piItemsPdfUrls.add(ProFormaInvoiceItemValue.getFileUrl());
                }
            }
        }).toList();
        byte[] pdfContent = pdfGenerationService.generateProformaInvoice(proFormaInvoiceValue, reportDetails);
        String base64PdfContent = Base64.getEncoder().encodeToString(pdfContent);


        List<String> base64PdfForMerging = new ArrayList<>();
        base64PdfForMerging.add(base64PdfContent);
        piItemsPdfUrls.stream().peek(piItemsPdfUrl -> {
            base64PdfForMerging.add(pdfService.getPdfAsBase64(piItemsPdfUrl));
        }).toList();


        String mergedPdf = pdfService.mergePDFs(base64PdfForMerging);
        byte[] finalPdf = Base64.getDecoder().decode(mergedPdf);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(finalPdf);
    }

    @PostMapping("/workOrder/{tenantUuid}/{proFormaInvoiceUuid}")
    public ResponseEntity<byte[]> generateWorkOrderPdf(@PathVariable String tenantUuid,
                                                       @PathVariable String proFormaInvoiceUuid) throws IOException {
        ProFormaInvoiceValue proFormaInvoiceValue = proFormaInvoiceService.getProFormaInvoice(tenantUuid, proFormaInvoiceUuid);
        CompanyInfoProjection billTo = companyService.getCompanyInfo(proFormaInvoiceValue.getTenantUuid(), proFormaInvoiceValue.getPartyBillToUuid());
        CompanyInfoProjection shipTo = companyService.getCompanyInfo(proFormaInvoiceValue.getTenantUuid(), proFormaInvoiceValue.getPartyShipToUuid());
        PIReportDetails reportDetails = new PIReportDetails();
        reportDetails.setBillTo(billTo);
        reportDetails.setShipTo(shipTo);
        List<String> piItemsPdfUrls = new ArrayList<>();
        proFormaInvoiceValue.getProFormaInvoiceItems().stream().peek(ProFormaInvoiceItemValue -> {
            if (ProFormaInvoiceItemValue.getFileUrl() != null) {
                if (!ProFormaInvoiceItemValue.getFileUrl().equals("Error handling the PDF.")) {
                    piItemsPdfUrls.add(ProFormaInvoiceItemValue.getFileUrl());
                }
            }
        }).toList();
        byte[] pdfContent = pdfGenerationService.generateWorkOrder(proFormaInvoiceValue, reportDetails);
        String base64PdfContent = Base64.getEncoder().encodeToString(pdfContent);


        List<String> base64PdfForMerging = new ArrayList<>();
        base64PdfForMerging.add(base64PdfContent);
        piItemsPdfUrls.stream().peek(piItemsPdfUrl -> {
            base64PdfForMerging.add(pdfService.getPdfAsBase64(piItemsPdfUrl));
        }).toList();


        String mergedPdf = pdfService.mergePDFs(base64PdfForMerging);
        byte[] finalPdf = Base64.getDecoder().decode(mergedPdf);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(finalPdf);
    }

    @PostMapping("/sticker")
    public ResponseEntity<byte[]> generateToughenSticker(@RequestBody StickerReportDto stickerReportDto) throws IOException {

        byte[] pdfContent = pdfGenerationService.generateToughenSticker(stickerReportDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

    @PostMapping("/gatePass/{tenantUuid}/{companyUuid}/{gatePassUuid}")
    public ResponseEntity<byte[]> generateGatePass(@PathVariable String tenantUuid,
                                                   @PathVariable String companyUuid,
                                                   @PathVariable String gatePassUuid
    ) throws IOException {
        GatePassRequestDto gatePassRequestDto = new GatePassRequestDto();
        gatePassRequestDto.setTenantUuid(tenantUuid);
        gatePassRequestDto.setCompanyUuid(companyUuid);
        gatePassRequestDto.setGatePassUuid(gatePassUuid);
        byte[] pdfContent = pdfGenerationService.generateGatePass(gatePassRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

    @PostMapping("/toughenBatch")
    public ResponseEntity<byte[]> generateToughenBatchReport(@RequestBody ToughenBatchReportDto toughenBatchReportDto) throws IOException {

        byte[] pdfContent = pdfGenerationService.generateToughenBatch(toughenBatchReportDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }
}
