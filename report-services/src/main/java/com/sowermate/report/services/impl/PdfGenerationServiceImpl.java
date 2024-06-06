package com.sowermate.report.services.impl;


import com.sowermate.image.config.PdfStorageConfig;
import com.sowermate.image.services.ImageService;
import com.sowermate.image.services.PdfService;
import com.sowermate.report.controllers.PIReportHeaderDetails;
import com.sowermate.report.dtos.*;
import com.sowermate.report.services.PdfGenerationService;
import com.sowermate.tenantService.entities.minimal.CompletedGlassesProjection;
import com.sowermate.tenantService.entities.minimal.GlassInfoProjection;
import com.sowermate.tenantService.entities.minimal.StickerReportProjection;
import com.sowermate.tenantService.entities.value.GatePassValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import com.sowermate.tenantService.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfGenerationServiceImpl implements PdfGenerationService {
    private final TemplateEngine templateEngine;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private GatePassService gatePassService;

    @Autowired
    private GlassThicknessService glassThicknessService;

    @Autowired
    private PdfStorageConfig pdfStorageConfig;
    @Autowired
    private ToughenBatchProcessService toughenBatchProcessService;
    @Autowired
    private JbCreationService jbCreationService;

    @Autowired
    private CompanyService companyService;


    @Autowired
    public PdfGenerationServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public byte[] generateToughenSticker(StickerRequestDto stickerRequestDto) throws IOException {
        StickerReportProjection stickerReportProjection = toughenBatchProcessService.getStickerReport(stickerRequestDto.getTenantUuid(), stickerRequestDto.getCompanyUuid(), stickerRequestDto.getBatchItemUuid());
        if (stickerReportProjection == null) {
            stickerReportProjection = jbCreationService.getStickerData(stickerRequestDto.getBatchItemUuid());
        }

        byte[] pdfBytes = generatePdfForSticker(stickerReportProjection);
        pdfService.handlePdf(pdfBytes, "JAYDEEP", "SICKER", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }

    @Override
    public byte[] generateGatePass(GatePassRequestDto gatePassRequestDto) throws IOException {
        String gatePassUuid = gatePassRequestDto.getGatePassUuid();
        String tenantUuid = gatePassRequestDto.getTenantUuid();
        String companyUuid = gatePassRequestDto.getCompanyUuid();
        GatePassReportDto gatePassReportDto = new GatePassReportDto();
        List<String> gatePassTypes = new ArrayList<>();
        gatePassTypes.add("GATE PASS (WAREHOUSE COPY)");
        gatePassTypes.add("GATE PASS (GATE COPY)");
        gatePassTypes.add("GATE PASS (OFFICE COPY)");

        GatePassValue gatePassValue = gatePassService.getGatePass(gatePassUuid, tenantUuid, companyUuid);
        List<GlassInfoProjection> glassInfoProjection = gatePassService.getGlassInfoForReport(gatePassUuid);
        int totalQuantity = 0;
        for (GlassInfoProjection g : glassInfoProjection) {
            totalQuantity = totalQuantity + g.getGatePassItemQty();
        }
        gatePassReportDto.setTotalQuantity(totalQuantity);
        PiInfoProjectionForReport piInfoProjectionForReport = gatePassService.getPiInfoForReport(gatePassUuid);

        gatePassReportDto.setGatePassTypes(gatePassTypes);
        gatePassReportDto.setGatePassValue(gatePassValue);
        gatePassReportDto.setGlassInfoProjections(glassInfoProjection);
        gatePassReportDto.setPiNo(piInfoProjectionForReport.getPiNo());
        gatePassReportDto.setPartyName(piInfoProjectionForReport.getPartyName());
        gatePassReportDto.setPartyBillToName(piInfoProjectionForReport.getPartyBillToName());
        gatePassReportDto.setPiDate(LocalDate.from(piInfoProjectionForReport.getPiDate()));


        byte[] pdfBytes = generatePdfForGatePass(gatePassReportDto);
        pdfService.handlePdf(pdfBytes, "JAYDEEP", "SICKER", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }

    @Override
    public byte[] generateToughenBatch(ToughenBatchReportRequestDto toughenBatchReportRequestDto) throws IOException {
        ToughenBatchReportDto toughenBatchReportDto = new ToughenBatchReportDto();
        List<CompletedGlassesProjection> completedWorkOrders = toughenBatchProcessService.getCompletedGlassesForReport(toughenBatchReportRequestDto.getTenantUuid(), toughenBatchReportRequestDto.getCompanyUuid(), toughenBatchReportRequestDto.getBatchItemDate());
        List<CompletedGlassValue> completedGlassValueList = new ArrayList<>();
        completedWorkOrders.stream().peek(c -> {
            CompletedGlassValue completedGlassValue = new CompletedGlassValue();
            completedGlassValue.setThicknessId(c.getId());
            if (c.getUnitValue() > 0)
                completedGlassValue.setSqft((c.getUnitValue() / c.getTotalQuantity()) * c.getDispatchCompleted());
            else
                completedGlassValue.setSqft(0.0);
            completedGlassValueList.add(completedGlassValue);
        }).collect(Collectors.toList());

        Map<Long, Double> aggregatedMap = completedGlassValueList.stream()
                .collect(Collectors.groupingBy(
                        CompletedGlassValue::getThicknessId,
                        Collectors.summingDouble(CompletedGlassValue::getSqft)
                ));

        List<CompletedGlassValue> aggregatedList = aggregatedMap.entrySet().stream()
                .map(entry -> {
                    CompletedGlassValue aggregatedValue = new CompletedGlassValue();
                    aggregatedValue.setThicknessId(entry.getKey());
                    aggregatedValue.setThickness(glassThicknessService.getGlassThicknessNameById(entry.getKey()));
                    aggregatedValue.setSqft(entry.getValue());
                    return aggregatedValue;
                })
                .toList();

        //TODO need to add data for jb

        toughenBatchReportDto.setCompletedWorkOrders(aggregatedList);
        toughenBatchReportDto.setDate(toughenBatchReportRequestDto.getBatchItemDate());
        toughenBatchReportDto.setPartyName(companyService.getCompanyName(toughenBatchReportRequestDto.getCompanyUuid()));
        byte[] pdfBytes = generatePdfForToughenBatch(toughenBatchReportDto);
        pdfService.handlePdf(pdfBytes, "JAYDEEP", "SICKER", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }

    @Override
    public byte[] generateProformaInvoice(ProFormaInvoiceValue piValue, PIReportDetails reportDetails) throws IOException {
        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails = glassItemDetails(piValue);

        extractCommonLogic(piValue, reportDetails, glassItemDetails);

        byte[] pdfBytes = generatePdf(piValue, reportDetails, "proforma-invoice");
        pdfService.handlePdf(pdfBytes, piValue.getProFormaInvoiceUuid(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }

    @Override
    public byte[] generateWorkOrder(ProFormaInvoiceValue piValue, PIReportDetails reportDetails) throws IOException {
        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails = glassItemDetails(piValue);

        extractCommonLogic(piValue, reportDetails, glassItemDetails);

        byte[] pdfBytes = generatePdf(piValue, reportDetails, "work-order");

        pdfService.handlePdf(pdfBytes, piValue.getProFormaInvoiceUuid(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }

    private byte[] generatePdf(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, String template) {
        try {
            Context context = new Context();
            context.setVariable("piValue", piValue);
            context.setVariable("reportDetails", reportDetails);

            String htmlContent = templateEngine.process(template, context);

            ITextRenderer renderer = new ITextRenderer(1000, 710);
            renderer.getSharedContext().setBaseURL("classpath:/static/");
            renderer.setDocumentFromString(htmlContent);

            renderer.layout();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            renderer.createPDF(outputStream);
            renderer.finishPDF();
            return outputStream.toByteArray();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] generatePdfForSticker(StickerReportProjection stickerData) {
        try {
            Context context = new Context();
            context.setVariable("stickerData", stickerData);

            String htmlContent = templateEngine.process("sticker", context);

            ITextRenderer renderer = new ITextRenderer(1000, 710);
            renderer.getSharedContext().setBaseURL("classpath:/static/");
            renderer.setDocumentFromString(htmlContent);

            renderer.layout();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            renderer.createPDF(outputStream);
            renderer.finishPDF();
            return outputStream.toByteArray();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] generatePdfForGatePass(GatePassReportDto gatePassReportDto) {
        try {
            Context context = new Context();
            context.setVariable("gatePass", gatePassReportDto);

            String htmlContent = templateEngine.process("gate-pass", context);

            ITextRenderer renderer = new ITextRenderer(1000, 710);
            renderer.getSharedContext().setBaseURL("classpath:/static/");
            renderer.setDocumentFromString(htmlContent);

            renderer.layout();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            renderer.createPDF(outputStream);
            renderer.finishPDF();
            return outputStream.toByteArray();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] generatePdfForToughenBatch(ToughenBatchReportDto toughenBatchReportDto) {
        try {
            Context context = new Context();
            context.setVariable("toughen", toughenBatchReportDto);

            String htmlContent = templateEngine.process("toughen-batch", context);

            ITextRenderer renderer = new ITextRenderer(1000, 710);
            renderer.getSharedContext().setBaseURL("classpath:/static/");
            renderer.setDocumentFromString(htmlContent);

            renderer.layout();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            renderer.createPDF(outputStream);
            renderer.finishPDF();
            return outputStream.toByteArray();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void extractCommonLogic(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails) {
        double totalQuantity = glassItemDetails.values().stream()
                .flatMap(List::stream)
                .mapToDouble(ProFormaInvoiceItemValue::getQuantity)
                .sum();
        double totalUnitTotal = glassItemDetails.values().stream()
                .flatMap(List::stream)
                .mapToDouble(ProFormaInvoiceItemValue::getUnitValue)
                .sum();
        double totalRatePerUnit = glassItemDetails.values().stream()
                .flatMap(List::stream)
                .mapToDouble(ProFormaInvoiceItemValue::getRatePerUnit)
                .sum();
        double totalAmount = glassItemDetails.values().stream()
                .flatMap(List::stream)
                .mapToDouble(ProFormaInvoiceItemValue::getAmount)
                .sum();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedTotalQuantity = decimalFormat.format(totalQuantity);
        String formattedTotalUnitTotal = decimalFormat.format(totalUnitTotal);
        String formattedTotalRatePerUnit = decimalFormat.format(totalRatePerUnit);
        String formattedTotalAmount = decimalFormat.format(totalAmount);

        reportDetails.setGlassItemDetails(glassItemDetails);
        reportDetails.setServiceRateDetails(serviceRateDetails(piValue));
        reportDetails.setTotalQuantity(formattedTotalQuantity);
        reportDetails.setTotalUnitTotal(formattedTotalUnitTotal);
        reportDetails.setTotalRatePerUnit(formattedTotalRatePerUnit);
        reportDetails.setTotalAmount(formattedTotalAmount);
        reportDetails.setUnitLabel(piValue.getPiTypeName().equals("MM") ? "Sq.mtr" : "Sq.ft");

        reportDetails.setGstType("Maharashtra".equalsIgnoreCase(reportDetails.getBillTo().getState()) ? "SGST-CGST" : "IGST");
        reportDetails.setIGst("" + (!ObjectUtils.isEmpty(piValue.getGstCharges()) ? piValue.getGstCharges() : 0));
        reportDetails.setSGst("" + (!ObjectUtils.isEmpty(piValue.getGstCharges()) ? piValue.getGstCharges() / 2 : 0));
        reportDetails.setCGst("" + (!ObjectUtils.isEmpty(piValue.getGstCharges()) ? piValue.getGstCharges() / 2 : 0));
        reportDetails.setIPercent((!ObjectUtils.isEmpty(piValue.getInsurancePercent()) && piValue.getInsurancePercent() > 0) ? piValue.getInsurancePercent() + "" : "0");
        reportDetails.setUPercent((!ObjectUtils.isEmpty(piValue.getInsurancePercent()) && piValue.getUrgencyPercent() > 0) ? piValue.getUrgencyPercent() + "" : "0");
        reportDetails.setIPercentAmount((ObjectUtils.isEmpty(piValue.getInsurancePercentAmount()) ? "0" : piValue.getInsurancePercentAmount()  + ""));
        reportDetails.setUPercentAmount((ObjectUtils.isEmpty(piValue.getUrgencyPercentAmount()) ? "0" : piValue.getUrgencyPercentAmount() + ""));
        reportDetails.setGrandTotal(ObjectUtils.isEmpty(piValue.getGrandTotal()) ? 0 : Math.round(piValue.getGrandTotal()));
    }

    private List<ServiceRateInvoiceValue> serviceRateDetails(ProFormaInvoiceValue piValue) {
        return piValue.getServiceRateInvoices();
    }

    private Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails(ProFormaInvoiceValue piValue) {
        List<ProFormaInvoiceItemValue> itemList = piValue.getProFormaInvoiceItems();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails = itemList.stream()
                .collect(Collectors.groupingBy(
                        glass -> new PIReportHeaderDetails(
                                glass.getGlassSpecificationName(),
                                glass.getGlassThicknessName(),
                                itemList.stream()
                                        .filter(g -> g.getGlassSpecificationName().equals(glass.getGlassSpecificationName()) &&
                                                g.getGlassThicknessName().equals(glass.getGlassThicknessName()))
                                        .mapToDouble(item -> Double.parseDouble(decimalFormat.format(item.getUnitValue())))
                                        .sum()
                        )
                ));
        return glassItemDetails;
    }


}
