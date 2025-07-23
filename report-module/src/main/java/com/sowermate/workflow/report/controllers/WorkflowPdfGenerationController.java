package com.sowermate.workflow.report.controllers;

import com.sowermate.core.image.services.PdfService;
import com.sowermate.core.tenant.projections.TenantInfoProjection;
import com.sowermate.core.tenant.services.TenantService;
import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceValue;
import com.sowermate.workflow.report.config.PdfStorageConfigWorkflow;
import com.sowermate.workflow.report.dtos.GatePassRequestDto;
import com.sowermate.workflow.report.dtos.PIReportDetails;
import com.sowermate.workflow.report.dtos.StickerRequestDto;
import com.sowermate.workflow.report.dtos.ToughenBatchReportRequestDto;
import com.sowermate.workflow.report.services.PdfGenerationUtils;
import com.sowermate.workflow.report.services.WorkflowPdfGenerationService;
import com.sowermate.workflow.service.services.ProFormaInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pdf")
public class WorkflowPdfGenerationController {
    @Autowired
    private WorkflowPdfGenerationService workflowPdfGenerationService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ProFormaInvoiceService proFormaInvoiceService;

    @Autowired
    private PdfStorageConfigWorkflow pdfStorageConfigWorkflow;

    @Autowired
    private TenantService tenantService;

    @PostMapping("/{tenantUuid}/{proFormaInvoiceUuid}")
    public ResponseEntity<byte[]> generateInvoicePdf(@PathVariable String tenantUuid,
                                                     @PathVariable String proFormaInvoiceUuid) throws IOException {
        ProFormaInvoiceValue proFormaInvoiceValue = proFormaInvoiceService.getProFormaInvoice(tenantUuid, proFormaInvoiceUuid);
        TenantInfoProjection billToAddress = tenantService.getTenantInfo(proFormaInvoiceValue.getTenantUuid());
        TenantInfoProjection shipToAddress = tenantService.getTenantInfo(proFormaInvoiceValue.getTenantUuid());
        String tenantName = tenantService.getTenantName(proFormaInvoiceValue.getFirmUuid());
        PIReportDetails reportDetails = new PIReportDetails();

        reportDetails.setBillToAddress(PdfGenerationUtils.extractedAddressInfo(billToAddress));
        reportDetails.setBillToPartyName(proFormaInvoiceValue.getPartyBillToName());
        reportDetails.setShipToAddress(PdfGenerationUtils.extractedAddressInfo(shipToAddress));
        reportDetails.setShipToPartyName(proFormaInvoiceValue.getPartyShipToName());
        reportDetails.setShippingAddress(proFormaInvoiceValue.getShippingAddress() != null ? proFormaInvoiceValue.getShippingAddress().replaceAll("\n", "<br/>") : null);
        reportDetails.setBillToPartyStateCode(billToAddress.getStateCode());
        reportDetails.setBillToAddress(PdfGenerationUtils.extractedAddressInfo(billToAddress));
        reportDetails.setFirmName(tenantName);

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
        byte[] pdfContent = workflowPdfGenerationService.generateProformaInvoice(proFormaInvoiceValue, reportDetails, designs);
        String base64PdfContent = Base64.getEncoder().encodeToString(pdfContent);


        List<String> base64PdfForMerging = new ArrayList<>();
        base64PdfForMerging.add(base64PdfContent);
        pdfs.stream().peek(base64PdfForMerging::add).toList();


        String mergedPdf = pdfService.mergePDFs(base64PdfForMerging);
        byte[] finalPdf = Base64.getDecoder().decode(mergedPdf);
        pdfService.handlePdf(finalPdf, proFormaInvoiceUuid, "invoice", pdfStorageConfigWorkflow.getProductInvoicesDirectory());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(finalPdf);
    }

    @PostMapping("/rough-glass/{tenantUuid}/{proFormaInvoiceUuid}")
    public ResponseEntity<byte[]> generateProformaInvoicePdfRoughGlass(@PathVariable String tenantUuid,
                                                                       @PathVariable String proFormaInvoiceUuid) throws IOException {
        ProFormaInvoiceValue proFormaInvoiceValue = proFormaInvoiceService.getProFormaInvoice(tenantUuid, proFormaInvoiceUuid);
        TenantInfoProjection billToAddress = tenantService.getTenantInfo(proFormaInvoiceValue.getTenantUuid());
        TenantInfoProjection shipToAddress = tenantService.getTenantInfo(proFormaInvoiceValue.getTenantUuid());
        PIReportDetails reportDetails = new PIReportDetails();

        reportDetails.setBillToAddress(PdfGenerationUtils.extractedAddressInfo(billToAddress));
        reportDetails.setBillToPartyName(proFormaInvoiceValue.getPartyBillToName());
        reportDetails.setShipToAddress(PdfGenerationUtils.extractedAddressInfo(shipToAddress));
        reportDetails.setShipToPartyName(proFormaInvoiceValue.getPartyShipToName());
        reportDetails.setShippingAddress(proFormaInvoiceValue.getShippingAddress() != null ? proFormaInvoiceValue.getShippingAddress().replaceAll("\n", "<br/>") : null);
        reportDetails.setBillToPartyStateCode(billToAddress.getStateCode());
        reportDetails.setBillToAddress(PdfGenerationUtils.extractedAddressInfo(billToAddress));

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
        byte[] pdfContent = workflowPdfGenerationService.generateProformaInvoicePdfRoughGlass(proFormaInvoiceValue, reportDetails, designs);
        String base64PdfContent = Base64.getEncoder().encodeToString(pdfContent);


        List<String> base64PdfForMerging = new ArrayList<>();
        base64PdfForMerging.add(base64PdfContent);
        pdfs.stream().peek(base64PdfForMerging::add).toList();


        String mergedPdf = pdfService.mergePDFs(base64PdfForMerging);
        byte[] finalPdf = Base64.getDecoder().decode(mergedPdf);
        pdfService.handlePdf(finalPdf, proFormaInvoiceUuid, "invoice", pdfStorageConfigWorkflow.getProductInvoicesDirectory());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(finalPdf);
    }


    @PostMapping("/workOrder/{tenantUuid}/{proFormaInvoiceUuid}")
    public ResponseEntity<byte[]> generateWorkOrderPdf(@PathVariable String tenantUuid,
                                                       @PathVariable String proFormaInvoiceUuid) throws IOException {
        ProFormaInvoiceValue proFormaInvoiceValue = proFormaInvoiceService.getProFormaInvoice(tenantUuid, proFormaInvoiceUuid);
        TenantInfoProjection billToAddress = tenantService.getTenantInfo(proFormaInvoiceValue.getTenantUuid());
        TenantInfoProjection shipToAddress = tenantService.getTenantInfo(proFormaInvoiceValue.getTenantUuid());
        String companyName = tenantService.getTenantName(proFormaInvoiceValue.getFirmUuid());
        PIReportDetails reportDetails = new PIReportDetails();
        reportDetails.setBillToAddress(PdfGenerationUtils.extractedAddressInfo(billToAddress));
        reportDetails.setBillToPartyName(proFormaInvoiceValue.getPartyBillToName());
        reportDetails.setShipToAddress(PdfGenerationUtils.extractedAddressInfo(shipToAddress));
        reportDetails.setShipToPartyName(proFormaInvoiceValue.getPartyShipToName());
        reportDetails.setShippingAddress(proFormaInvoiceValue.getShippingAddress() != null ? proFormaInvoiceValue.getShippingAddress().replaceAll("\n", "<br/>") : null);
        reportDetails.setBillToPartyStateCode(billToAddress.getStateCode()/*.get(0).getStateCode()*/);
        reportDetails.setBillToAddress(PdfGenerationUtils.extractedAddressInfo(billToAddress));
        reportDetails.setFirmName(companyName);

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

        byte[] pdfContent = workflowPdfGenerationService.generateWorkOrder(proFormaInvoiceValue, reportDetails, designs);
        String base64PdfContent = Base64.getEncoder().encodeToString(pdfContent);

        List<String> base64PdfForMerging = new ArrayList<>();
        base64PdfForMerging.add(base64PdfContent);
        pdfs.stream().peek(base64PdfForMerging::add).toList();

        String mergedPdf = pdfService.mergePDFs(base64PdfForMerging);
        byte[] finalPdf = Base64.getDecoder().decode(mergedPdf);
        pdfService.handlePdf(finalPdf, proFormaInvoiceUuid, "invoice", pdfStorageConfigWorkflow.getProductInvoicesDirectory());

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
        byte[] pdfContent = workflowPdfGenerationService.generateToughenSticker(stickerRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

    @PostMapping("/all-stickers/{tenantUuid}/{companyUuid}/{batchUuid}")
    public ResponseEntity<byte[]> generateToughenStickers(@PathVariable String tenantUuid,
                                                          @PathVariable String companyUuid,
                                                          @PathVariable String batchUuid) throws IOException {

        StickerRequestDto stickerRequestDto = new StickerRequestDto();

        stickerRequestDto.setTenantUuid(tenantUuid);
        stickerRequestDto.setCompanyUuid(companyUuid);
        stickerRequestDto.setBatchUuid(batchUuid);
        byte[] pdfContent = workflowPdfGenerationService.generateToughenStickers(stickerRequestDto);
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
        byte[] pdfContent = workflowPdfGenerationService.generateGatePass(gatePassRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

    @PostMapping("/toughenBatch")
    public ResponseEntity<byte[]> generateToughenBatchReport(@RequestBody ToughenBatchReportRequestDto toughenBatchReportRequestDto) throws IOException {

        byte[] pdfContent = workflowPdfGenerationService.generateToughenBatch(toughenBatchReportRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/pdf"));
        headers.setContentDispositionFormData("attachment", "example.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

    @PostMapping("/toughenReport")
    public ResponseEntity<byte[]> generateToughenReport(@RequestParam LocalDate date) throws IOException {

        byte[] pdfContent = workflowPdfGenerationService.generateToughenReport(date);
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

    private boolean isPdf(String fileBase64) {
        byte[] fileBytes = Base64.getDecoder().decode(fileBase64);
        return fileBytes.length > 4 && fileBytes[0] == 0x25 && fileBytes[1] == 0x50 && fileBytes[2] == 0x44 && fileBytes[3] == 0x46;
    }
}
