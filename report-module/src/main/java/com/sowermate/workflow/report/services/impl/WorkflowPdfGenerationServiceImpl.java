package com.sowermate.workflow.report.services.impl;

import com.sowermate.core.image.services.ImageService;
import com.sowermate.core.image.services.PdfService;
import com.sowermate.core.tenant.projections.TenantInfoProjection;
import com.sowermate.core.tenant.services.TenantService;
import com.sowermate.workflow.domain.entities.minimal.CompletedGlassesProjection;
import com.sowermate.workflow.domain.entities.minimal.GlassInfoProjection;
import com.sowermate.workflow.domain.entities.minimal.StickerReportProjection;
import com.sowermate.workflow.domain.entities.value.GatePassValue;
import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceItemReportValue;
import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceValue;
import com.sowermate.workflow.domain.entities.value.ServiceRateInvoiceValue;
import com.sowermate.workflow.domain.entities.value.ToughReportDto;
import com.sowermate.workflow.domain.projection.PiInfoProjectionForReport;
import com.sowermate.workflow.report.config.PdfStorageConfigWorkflow;
import com.sowermate.workflow.report.controllers.PIReportHeaderDetails;
import com.sowermate.workflow.report.dtos.CompletedGlassValue;
import com.sowermate.workflow.report.dtos.GatePassReportDto;
import com.sowermate.workflow.report.dtos.GatePassRequestDto;
import com.sowermate.workflow.report.dtos.PIReportDetails;
import com.sowermate.workflow.report.dtos.StickerRequestDto;
import com.sowermate.workflow.report.dtos.ToughenBatchReportDto;
import com.sowermate.workflow.report.dtos.ToughenBatchReportRequestDto;
import com.sowermate.workflow.report.services.PdfGenerationUtils;
import com.sowermate.workflow.report.services.WorkflowPdfGenerationService;
import com.sowermate.workflow.service.services.GatePassService;
import com.sowermate.workflow.service.services.GlassThicknessService;
import com.sowermate.workflow.service.services.JbCreationService;
import com.sowermate.workflow.service.services.ToughenBatchProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkflowPdfGenerationServiceImpl implements WorkflowPdfGenerationService {
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
    private PdfStorageConfigWorkflow pdfStorageConfig;
    @Autowired
    private ToughenBatchProcessService toughenBatchProcessService;
    @Autowired
    private JbCreationService jbCreationService;

    @Autowired
    private TenantService tenantService;


    @Autowired
    public WorkflowPdfGenerationServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public byte[] generateToughenSticker(StickerRequestDto stickerRequestDto) throws IOException {
        StickerReportProjection stickerReportProjection = toughenBatchProcessService.getStickerOfBatchItem(stickerRequestDto.getTenantUuid(), stickerRequestDto.getCompanyUuid(), stickerRequestDto.getBatchItemUuid());
        if (stickerReportProjection == null) {
            stickerReportProjection = jbCreationService.getStickerData(stickerRequestDto.getBatchItemUuid());
            if (stickerReportProjection == null) {
                throw new RuntimeException("data not found");
            }
        }

        byte[] pdfBytes = generatePdfForSticker(Collections.singletonList(stickerReportProjection));
        pdfService.handlePdf(pdfBytes, "JAYDEEP", "SICKER", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }

    @Override
    public byte[] generateToughenStickers(StickerRequestDto stickerRequestDto) throws IOException {
        List<StickerReportProjection> stickerReportProjection = toughenBatchProcessService.getStickersOfBatch(stickerRequestDto.getTenantUuid(), stickerRequestDto.getCompanyUuid(), stickerRequestDto.getBatchUuid());
        if (stickerReportProjection != null) {
            byte[] pdfBytes = generatePdfForSticker(stickerReportProjection);
            pdfService.handlePdf(pdfBytes, "JAYDEEP", "SICKER", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
            return pdfBytes;
        } else {
            throw new RuntimeException("data not found");
        }
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

        TenantInfoProjection companyInfo = tenantService.getTenantInfo(gatePassRequestDto.getTenantUuid());
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
        gatePassReportDto.setGatePassDate(LocalDate.from(gatePassValue.getCreatedDateTime()));
        //AddressEntity address = companyInfo.getAddresses().get(0);
        gatePassReportDto.setAddress(companyInfo.getAddressLine1() + ", " + PdfGenerationUtils.getStateName(companyInfo.getStateCode()) + ", " + companyInfo.getPinCode());

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
            else completedGlassValue.setSqft(0.0);
            completedGlassValueList.add(completedGlassValue);
        }).collect(Collectors.toList());

//        Map<Long, Double> aggregatedMap = completedGlassValueList.stream()
//                .collect(Collectors.groupingBy(
//                        CompletedGlassValue::getThicknessId,
//                        Collectors.summingDouble(CompletedGlassValue::getSqft)
//                ));
//
//        List<CompletedGlassValue> aggregatedList = aggregatedMap.entrySet().stream()
//                .map(entry -> {
//                    CompletedGlassValue aggregatedValue = new CompletedGlassValue();
//                    aggregatedValue.setThicknessId(entry.getKey());
//                    aggregatedValue.setThickness(glassThicknessService.getGlassThicknessNameById(entry.getKey()));
//                    aggregatedValue.setSqft(entry.getValue());
//                    return aggregatedValue;
//                })
//                .toList();

        Map<Long, Double> aggregatedMap = completedGlassValueList.stream().collect(Collectors.groupingBy(CompletedGlassValue::getThicknessId, Collectors.summingDouble(CompletedGlassValue::getSqft)));

        List<CompletedGlassValue> aggregatedList = aggregatedMap.entrySet().stream().map(entry -> {
            CompletedGlassValue aggregatedValue = new CompletedGlassValue();
            aggregatedValue.setThicknessId(entry.getKey());
            aggregatedValue.setThickness(glassThicknessService.getGlassThicknessNameById(entry.getKey()));
            aggregatedValue.setSqft(entry.getValue());
            return aggregatedValue;
        }).toList();


        //TODO need to add data for jb

        toughenBatchReportDto.setCompletedWorkOrders(aggregatedList);
        toughenBatchReportDto.setDate(toughenBatchReportRequestDto.getBatchItemDate());
        toughenBatchReportDto.setPartyName(tenantService.getTenantName(toughenBatchReportRequestDto.getCompanyUuid()));
        byte[] pdfBytes = generatePdfForToughenBatch(toughenBatchReportDto);
        pdfService.handlePdf(pdfBytes, "JAYDEEP", "SICKER", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }

//    @Override
//    public byte[] generateToughenReport(LocalDate date) throws IOException {
//        ToughReportDto toughReportDto = new ToughReportDto();
//        List<ToughenReportProjection> toughenReportProjection = toughenBatchProcessService.getToughenReportForReport(date);
//        byte[] pdfBytes = generatePdfForToughenReport(toughReportDto);
//        pdfService.handlePdf(pdfBytes, "JAYDEEP", "SICKER", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
//        return pdfBytes;
//    }

    @Override
    public byte[] generateToughenReport(LocalDate date) throws IOException {
        ToughReportDto toughenReportData = toughenBatchProcessService.getToughenReportForReport(date);

        byte[] pdfBytes = generatePdfForToughenReport(toughenReportData);
        pdfService.handlePdf(pdfBytes, "JAYDEEP", "SICKER", pdfStorageConfig.getProductInvoicesDirectory());
        return pdfBytes;
    }


    @Override
    public byte[] generateProformaInvoice(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs) throws IOException {
        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails = glassItemDetails(piValue);

        String companyName = tenantService.getTenantName(piValue.getFirmUuid());
        extractCommonLogic(piValue, reportDetails, glassItemDetails);
        byte[] pdfBytes;
        if (!companyName.isBlank() && companyName.equalsIgnoreCase("HIMYOUG TUFF GLASS INDUSTRIES PVT. LTD.")) {
            pdfBytes = generatePdf(piValue, reportDetails, designs, "proforma-invoice");
        } else {
            pdfBytes = generatePdf(piValue, reportDetails, designs, "proforma-invoice-another-company");
        }
        pdfService.handlePdf(pdfBytes, piValue.getUuid(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }

    @Override
    public byte[] generateProformaInvoicePdfRoughGlass(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs) throws IOException {
        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails = glassItemDetails(piValue);
        String companyName = tenantService.getTenantName(piValue.getFirmUuid());
        extractCommonLogic(piValue, reportDetails, glassItemDetails);
        byte[] pdfBytes;
        if (!companyName.isBlank() && companyName.equalsIgnoreCase("HIMYOUG TUFF GLASS INDUSTRIES PVT. LTD.")) {
            pdfBytes = generatePdf(piValue, reportDetails, designs, "proforma-invoice-rough-glass");
        } else {
            pdfBytes = generatePdf(piValue, reportDetails, designs, "proforma-invoice-company-rough-glass");
        }
        pdfService.handlePdf(pdfBytes, piValue.getUuid(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }


    @Override
    public byte[] generateWorkOrder(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs) throws IOException {
        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails = glassItemDetails(piValue);

        extractCommonLogic(piValue, reportDetails, glassItemDetails);

        String templateName = reportDetails.getFirmName();

        byte[] pdfBytes = generatePdf(piValue, reportDetails, designs, "work-order");

        pdfService.handlePdf(pdfBytes, piValue.getUuid(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }

    private byte[] generatePdf(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs, String template) {
        try {
            Context context = new Context();
            context.setVariable("piValue", piValue);
            context.setVariable("reportDetails", reportDetails);
            context.setVariable("designs", designs);

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

    private byte[] generatePdfForSticker(List<StickerReportProjection> stickerDataList) {
        try {
            Context context = new Context();
            context.setVariable("stickerDataList", stickerDataList);

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

    private byte[] generatePdfForToughenReport(ToughReportDto toughReportDto) {
        try {
            if (toughReportDto == null) {
                throw new IllegalArgumentException("ToughReportDto cannot be null");
            }

            Context context = new Context();
            context.setVariable("toughen", toughReportDto);


            String htmlContent = templateEngine.process("toughen-report", context);

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

    private void extractCommonLogic(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails) {
        double totalQuantity = glassItemDetails.values().stream().flatMap(List::stream).mapToDouble(ProFormaInvoiceItemReportValue::getQuantity).sum();
        BigDecimal totalUnitTotal = BigDecimal.ZERO;
        BigDecimal sumSqFtTotal = BigDecimal.ZERO;
        for (List<ProFormaInvoiceItemReportValue> itemList : glassItemDetails.values()) {
            for (ProFormaInvoiceItemReportValue item : itemList) {
                totalUnitTotal = totalUnitTotal.add(item.getUnitValue());
                sumSqFtTotal = sumSqFtTotal.add(new BigDecimal(item.getSqFt()));
            }
        }

        double totalRatePerUnit = glassItemDetails.values().stream().flatMap(List::stream).mapToDouble(ProFormaInvoiceItemReportValue::getRatePerUnit).sum();
        double totalAmount = glassItemDetails.values().stream().flatMap(List::stream).mapToDouble(ProFormaInvoiceItemReportValue::getAmount).sum();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedTotalQuantity = decimalFormat.format(totalQuantity);
        String formattedTotalUnitTotal = decimalFormat.format(totalUnitTotal);
        String formattedSumSqFtTotal = decimalFormat.format(sumSqFtTotal);
        String formattedTotalRatePerUnit = decimalFormat.format(totalRatePerUnit);
        String formattedTotalAmount = decimalFormat.format(totalAmount);

        reportDetails.setGlassItemDetails(glassItemDetails);
        reportDetails.setServiceRateDetails(serviceRateDetails(piValue));
        reportDetails.setTotalQuantity(formattedTotalQuantity);
        reportDetails.setTotalUnitTotal(formattedTotalUnitTotal);
        reportDetails.setSumSqFtTotal(formattedSumSqFtTotal);
        reportDetails.setTotalRatePerUnit(formattedTotalRatePerUnit);
        reportDetails.setTotalAmount(formattedTotalAmount);
        //reportDetails.setUnitLabel(piValue.getPiTypeName().equals("MM") ? "Sq.mtr" : "Sq.ft");
        reportDetails.setUnitLabel("MM".equals(piValue.getPiTypeName()) ? "Sq.mtr" : "Sq.ft");

        reportDetails.setGstType("MH".equalsIgnoreCase(reportDetails.getBillToPartyStateCode()) ? "SGST-CGST" : "IGST");
        reportDetails.setIGst(new DecimalFormat("#.##").format((!ObjectUtils.isEmpty(piValue.getGstCharges()) ? piValue.getGstCharges() : 0)));
        reportDetails.setSGst(new DecimalFormat("#.##").format((!ObjectUtils.isEmpty(piValue.getGstCharges()) ? piValue.getGstCharges() / 2 : 0)));
        reportDetails.setCGst(new DecimalFormat("#.##").format((!ObjectUtils.isEmpty(piValue.getGstCharges()) ? piValue.getGstCharges() / 2 : 0)));
        reportDetails.setIPercent((!ObjectUtils.isEmpty(piValue.getInsurancePercent()) && piValue.getInsurancePercent() > 0) ? piValue.getInsurancePercent() + "" : "0");
        reportDetails.setProxSqft((piValue.getProxSqft() != null ? piValue.getProxSqft() + "" : "0"));
        reportDetails.setIPercentAmount((ObjectUtils.isEmpty(piValue.getInsurancePercentAmount()) ? "0" : piValue.getInsurancePercentAmount() + ""));
        reportDetails.setProxSqftRate(piValue.getProxPerSqftRate() == null ? "0" : piValue.getProxPerSqftRate() + "");
        //reportDetails.setProxAmount(piValue.getProxCharges() > 0 ? piValue.getProxCharges() + "" : "0");
        reportDetails.setProxAmount(piValue.getProxCharges() != null ? (piValue.getProxCharges() > 0 ? piValue.getProxCharges() + "" : "0") : null);
        reportDetails.setGrandTotal(ObjectUtils.isEmpty(piValue.getGrandTotal()) ? 0.0 : Math.round(piValue.getGrandTotal()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Format the LocalDateTime instance to a string
        String formattedInvoiceDate = piValue.getInvoiceDate().format(formatter);
        String formattedWorkOrderDate = piValue.getWorkOrderValue() != null ? piValue.getWorkOrderValue().getCreatedDateTime().format(formatter) : "";

        reportDetails.setFormattedInvoiceDate(formattedInvoiceDate);
        reportDetails.setFormattedWorkOrderDate(formattedWorkOrderDate);

    }

    private List<ServiceRateInvoiceValue> serviceRateDetails(ProFormaInvoiceValue piValue) {
        return piValue.getServiceRateInvoices();
    }

    private Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails(ProFormaInvoiceValue piValue) {
        List<ProFormaInvoiceItemValue> itemList = piValue.getProFormaInvoiceItems();

        List<ProFormaInvoiceItemReportValue> itemsList = itemList.stream().map(this::toReportVal).toList();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails = itemsList.stream().collect(Collectors.groupingBy(glass -> new PIReportHeaderDetails(glass.getGlassSpecificationName(), glass.getGlassThicknessName(), decimalFormat.format(itemList.stream().filter(g -> g.getGlassSpecificationName().equals(glass.getGlassSpecificationName()) && g.getGlassThicknessName().equals(glass.getGlassThicknessName())).mapToDouble(item -> Double.parseDouble(decimalFormat.format(item.getUnitValue()))).sum()))));
        return glassItemDetails;
    }

    public ProFormaInvoiceItemReportValue toReportVal(ProFormaInvoiceItemValue value) {
        return ProFormaInvoiceItemReportValue.newBuilder().uuid(value.getUuid()).widthInch(value.getWidthInch()).widthMeasurement(value.getWidthMeasurement()).widthMeasurementLabel(value.getWidthMeasurementLabel()) //onPrintProformaInvoicePdf onDownloadWorkorderdetails
                .actualWidth(Math.round(value.getActualWidth()) + "").chargeableWidth(value.getChargeableWidth()).heightInch(value.getHeightInch()).heightMeasurement(value.getHeightMeasurement()).heightMeasurementLabel(value.getHeightMeasurementLabel()).actualHeight(Math.round(value.getActualHeight()) + "").chargeableHeight(value.getChargeableHeight()).extraMm(value.getExtraMm()).quantity(value.getQuantity()).unitValue(BigDecimal.valueOf(value.getUnitValue()).setScale(2, RoundingMode.HALF_UP)).ratePerUnit(value.getRatePerUnit()).unitMeasurementLabel(value.getUnitMeasurementLabel()).amount(value.getAmount()).sqFt(new DecimalFormat("#.00").format(value.getActualHeight() * value.getActualWidth() / 92903 * value.getQuantity()))

                .optimizeBucket(value.getOptimizeBucket() == null ? 0 : value.getOptimizeBucket()).cuttingBucket(value.getCuttingBucket() == null ? 0 : value.getCuttingBucket()).toughenBucket(value.getToughenBucket() == null ? 0 : value.getToughenBucket()).dispatchBucket(value.getDispatchBucket() == null ? 0 : value.getDispatchBucket()).gatePassBucket(value.getGatePassBucket() == null ? 0 : value.getGatePassBucket()).optimizeCompleted(value.getOptimizeCompleted() == null ? 0 : value.getOptimizeCompleted()).cuttingCompleted(value.getCuttingCompleted() == null ? 0 : value.getCuttingCompleted()).toughenCompleted(value.getToughenCompleted() == null ? 0 : value.getToughenCompleted()).dispatchCompleted(value.getDispatchCompleted() == null ? 0 : value.getDispatchCompleted()).gatePassCompleted(value.getGatePassCompleted() == null ? 0 : value.getGatePassCompleted()).glassSpecificationName(value.getGlassSpecificationName()).glassThicknessName(value.getGlassThicknessName()).glassTypeName(value.getGlassTypeName())

                .status(value.getStatus())
                .statusDetails(value.getStatusDetails())
                .isActive(value.getIsActive())
                .build();
    }
}
