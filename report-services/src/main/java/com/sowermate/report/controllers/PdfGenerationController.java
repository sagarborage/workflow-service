package com.sowermate.report.controllers;

import com.sowermate.image.config.PdfStorageConfig;
import com.sowermate.image.services.PdfService;
import com.sowermate.report.dtos.GatePassRequestDto;
import com.sowermate.report.dtos.PIReportDetails;
import com.sowermate.report.dtos.StickerRequestDto;
import com.sowermate.report.dtos.ToughenBatchReportRequestDto;
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
import java.util.*;

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
    private PdfStorageConfig pdfStorageConfig;

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
        reportDetails.setShippingAddress(proFormaInvoiceValue.getShippingAddress() != null ? proFormaInvoiceValue.getShippingAddress().replaceAll("\n", "<br/>") : null);
        List<String> piItemsPdfUrls = new ArrayList<>();
        proFormaInvoiceValue.getProFormaInvoiceItems().stream().peek(ProFormaInvoiceItemValue -> {
            if (ProFormaInvoiceItemValue.getFileUrl() != null) {
                if (!ProFormaInvoiceItemValue.getFileUrl().equals("Error handling the PDF.")) {
                    piItemsPdfUrls.add(ProFormaInvoiceItemValue.getFileUrl());
                }
            }
        }).toList();
        List<Map<Integer, String>> designs = new ArrayList<>();
        List<String> pdfs = new ArrayList<>();
        int[] count = {1};
        piItemsPdfUrls.forEach(piItemsPdfUrl -> {
            String designBase64 = pdfService.getPdfAsBase64(piItemsPdfUrl);
            Map<Integer, String> design = new HashMap<>();
            int designRank = count[0];
            if (isPdf(designBase64)) {
                pdfs.add(designBase64);
            } else {
                String imageUrl = "data:image/png;base64," + designBase64;
                design.put(designRank, imageUrl);
                designs.add(design);
            }
            count[0]++;
        });
        byte[] pdfContent = pdfGenerationService.generateProformaInvoice(proFormaInvoiceValue, reportDetails, designs);
        String base64PdfContent = Base64.getEncoder().encodeToString(pdfContent);


        List<String> base64PdfForMerging = new ArrayList<>();
        base64PdfForMerging.add(base64PdfContent);
        pdfs.stream().peek(base64PdfForMerging::add).toList();


        String mergedPdf = pdfService.mergePDFs(base64PdfForMerging);
        byte[] finalPdf = Base64.getDecoder().decode(mergedPdf);
        pdfService.handlePdf(finalPdf, proFormaInvoiceUuid, "invoice", pdfStorageConfig.getProductInvoicesDirectory());

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

        List<Map<Integer, String>> designs = new ArrayList<>();
        List<String> pdfs = new ArrayList<>();
        int[] count = {1};
        piItemsPdfUrls.forEach(piItemsPdfUrl -> {
            String designBase64 = pdfService.getPdfAsBase64(piItemsPdfUrl);
            Map<Integer, String> design = new HashMap<>();
            int designRank = count[0];
            if (isPdf(designBase64)) {
                pdfs.add(designBase64);
            } else {
                String imageUrl = "data:image/png;base64," + designBase64;
                design.put(designRank, imageUrl);
                designs.add(design);
            }
            count[0]++;
        });

        byte[] pdfContent = pdfGenerationService.generateWorkOrder(proFormaInvoiceValue, reportDetails, designs);
        String base64PdfContent = Base64.getEncoder().encodeToString(pdfContent);

        List<String> base64PdfForMerging = new ArrayList<>();
        base64PdfForMerging.add(base64PdfContent);
        pdfs.stream().peek(base64PdfForMerging::add).toList();

        String mergedPdf = pdfService.mergePDFs(base64PdfForMerging);
        byte[] finalPdf = Base64.getDecoder().decode(mergedPdf);
        pdfService.handlePdf(finalPdf, proFormaInvoiceUuid, "invoice", pdfStorageConfig.getProductInvoicesDirectory());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(finalPdf);
    }

    @PostMapping("/sticker/{tenantUuid}/{companyUuid}/{batchItemUuid}")
    public ResponseEntity<byte[]> generateToughenSticker(@PathVariable String tenantUuid,
                                                         @PathVariable String companyUuid,
                                                         @PathVariable String batchItemUuid) throws IOException {

        StickerRequestDto stickerRequestDto = new StickerRequestDto();

        stickerRequestDto.setTenantUuid(tenantUuid);
        stickerRequestDto.setCompanyUuid(companyUuid);
        stickerRequestDto.setBatchItemUuid(batchItemUuid);
        byte[] pdfContent = pdfGenerationService.generateToughenSticker(stickerRequestDto);
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
    public ResponseEntity<byte[]> generateToughenBatchReport(@RequestBody ToughenBatchReportRequestDto toughenBatchReportRequestDto) throws IOException {

        byte[] pdfContent = pdfGenerationService.generateToughenBatch(toughenBatchReportRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

    @PostMapping("/merge-files")
    public ResponseEntity<byte[]> mergeFiles(@RequestBody List<String> files) throws IOException {
        String mergedPdf = pdfService.mergePDFs(files);
        byte[] pdfContent = Base64.getDecoder().decode(mergedPdf);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

    @PostMapping("/test2/{tenantUuid}/{proFormaInvoiceUuid}")
    public ResponseEntity<byte[]> test2(@PathVariable String tenantUuid,
                                        @PathVariable String proFormaInvoiceUuid,
                                        @RequestBody ImageBody imageBody) throws IOException {
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

        List<String> files = new ArrayList<>();
        files.add(imageBody.getImageBase64().get(0));
        files.add(imageBody.getImageBase64().get(1));
        files.add(imageBody.getImageBase64().get(2));
        files.add(imageBody.getImageBase64().get(3));


        List<Map<Integer, String>> designs = new ArrayList<>();
        List<String> pdfs = new ArrayList<>();
        int[] count = {1};
        files.forEach(piItemsPdfUrl -> {
            //String designBase64 = pdfService.getPdfAsBase64(piItemsPdfUrl);
            Map<Integer, String> design = new HashMap<>();
            int designRank = count[0];
            if (isPdf(piItemsPdfUrl)) {
                pdfs.add(piItemsPdfUrl);
            } else {
                String imageUrl = "data:image/png;base64," + piItemsPdfUrl;
                design.put(designRank, imageUrl);
                designs.add(design);
            }
            count[0]++;
        });

        byte[] pdfContent = pdfGenerationService.generateProformaInvoice(proFormaInvoiceValue, reportDetails, designs);

        String base64PdfContent = Base64.getEncoder().encodeToString(pdfContent);


        List<String> base64PdfForMerging = new ArrayList<>();
        base64PdfForMerging.add(base64PdfContent);
        pdfs.stream().peek(base64PdfForMerging::add).toList();


        String mergedPdf = pdfService.mergePDFs(base64PdfForMerging);
        byte[] finalPdf = Base64.getDecoder().decode(mergedPdf);
        pdfService.handlePdf(finalPdf, proFormaInvoiceUuid, "invoice", pdfStorageConfig.getProductInvoicesDirectory());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(finalPdf);
    }

    private boolean isPdf(String fileBase64) {
        byte[] fileBytes = Base64.getDecoder().decode(fileBase64);
        return fileBytes.length > 4 && fileBytes[0] == 0x25 && fileBytes[1] == 0x50 && fileBytes[2] == 0x44 && fileBytes[3] == 0x46;
    }
}
