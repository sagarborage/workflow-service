package com.sowermate.report.services.impl;


import com.sowermate.image.config.PdfStorageConfig;
import com.sowermate.image.services.ImageService;
import com.sowermate.image.services.PdfService;
import com.sowermate.report.controllers.PIReportHeaderDetails;
import com.sowermate.report.dtos.CompletedGlassValue;
import com.sowermate.report.dtos.GatePassReportDto;
import com.sowermate.report.dtos.GatePassRequestDto;
import com.sowermate.report.dtos.PIReportDetails;
import com.sowermate.report.dtos.StickerRequestDto;
import com.sowermate.report.dtos.ToughenBatchReportDto;
import com.sowermate.report.dtos.ToughenBatchReportRequestDto;
import com.sowermate.report.services.PdfGenerationService;
import com.sowermate.report.services.PdfGenerationUtils;
import com.sowermate.tenantService.entities.AddressEntity;
import com.sowermate.tenantService.entities.minimal.CompanyInfoProjection;
import com.sowermate.tenantService.entities.minimal.CompletedGlassesProjection;
import com.sowermate.tenantService.entities.minimal.GlassInfoProjection;
import com.sowermate.tenantService.entities.minimal.StickerReportProjection;
import com.sowermate.tenantService.entities.value.GatePassValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemReportValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import com.sowermate.tenantService.services.CompanyService;
import com.sowermate.tenantService.services.GatePassService;
import com.sowermate.tenantService.services.GlassThicknessService;
import com.sowermate.tenantService.services.JbCreationService;
import com.sowermate.tenantService.services.PiInfoProjectionForReport;
import com.sowermate.tenantService.services.ToughenBatchProcessService;
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

        CompanyInfoProjection companyInfo = companyService.getCompanyInfo(gatePassRequestDto.getTenantUuid(), gatePassRequestDto.getCompanyUuid());
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
        AddressEntity address = companyInfo.getAddresses().get(0);
        gatePassReportDto.setAddress(address.getAddressLine1() + ", " + PdfGenerationUtils.getStateName(address.getStateCode()) + ", " + address.getPinCode());

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
    public byte[] generateProformaInvoice(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs) throws IOException {
        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails = glassItemDetails(piValue);

        extractCommonLogic(piValue, reportDetails, glassItemDetails);

        byte[] pdfBytes = generatePdf(piValue, reportDetails, designs, "proforma-invoice");
        pdfService.handlePdf(pdfBytes, piValue.getProFormaInvoiceUuid(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
        return pdfBytes;
    }

    @Override
    public byte[] generateWorkOrder(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs) throws IOException {
        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails = glassItemDetails(piValue);

        extractCommonLogic(piValue, reportDetails, glassItemDetails);

        byte[] pdfBytes = generatePdf(piValue, reportDetails, designs, "work-order");

        pdfService.handlePdf(pdfBytes, piValue.getProFormaInvoiceUuid(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
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

    private void extractCommonLogic(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails) {
        double totalQuantity = glassItemDetails.values().stream()
                .flatMap(List::stream)
                .mapToDouble(ProFormaInvoiceItemReportValue::getQuantity)
                .sum();
        BigDecimal totalUnitTotal = BigDecimal.ZERO;
        BigDecimal sumSqFtTotal = BigDecimal.ZERO;
        for (List<ProFormaInvoiceItemReportValue> itemList : glassItemDetails.values()) {
            for (ProFormaInvoiceItemReportValue item : itemList) {
                totalUnitTotal = totalUnitTotal.add(item.getUnitValue());
                sumSqFtTotal = sumSqFtTotal.add(new BigDecimal(item.getSqFt()));
            }
        }

        double totalRatePerUnit = glassItemDetails.values().stream()
                .flatMap(List::stream)
                .mapToDouble(ProFormaInvoiceItemReportValue::getRatePerUnit)
                .sum();
        double totalAmount = glassItemDetails.values().stream()
                .flatMap(List::stream)
                .mapToDouble(ProFormaInvoiceItemReportValue::getAmount)
                .sum();

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
        reportDetails.setUnitLabel(piValue.getPiTypeName().equals("MM") ? "Sq.mtr" : "Sq.ft");

        reportDetails.setGstType("MH".equalsIgnoreCase(reportDetails.getBillToPartyStateCode()) ? "SGST-CGST" : "IGST");
        reportDetails.setIGst(new DecimalFormat("#.##").format((!ObjectUtils.isEmpty(piValue.getGstCharges()) ? piValue.getGstCharges() : 0)));
        reportDetails.setSGst(new DecimalFormat("#.##").format((!ObjectUtils.isEmpty(piValue.getGstCharges()) ? piValue.getGstCharges() / 2 : 0)));
        reportDetails.setCGst(new DecimalFormat("#.##").format((!ObjectUtils.isEmpty(piValue.getGstCharges()) ? piValue.getGstCharges() / 2 : 0)));
        reportDetails.setIPercent((!ObjectUtils.isEmpty(piValue.getInsurancePercent()) && piValue.getInsurancePercent() > 0) ? piValue.getInsurancePercent() + "" : "0");
        reportDetails.setUPercent((!ObjectUtils.isEmpty(piValue.getInsurancePercent()) && piValue.getUrgencyPercent() > 0) ? piValue.getUrgencyPercent() + "" : "0");
        reportDetails.setIPercentAmount((ObjectUtils.isEmpty(piValue.getInsurancePercentAmount()) ? "0" : piValue.getInsurancePercentAmount() + ""));
        reportDetails.setUPercentAmount((ObjectUtils.isEmpty(piValue.getUrgencyPercentAmount()) ? "0" : piValue.getUrgencyPercentAmount() + ""));
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

        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails = itemsList.stream()
                .collect(Collectors.groupingBy(
                        glass -> new PIReportHeaderDetails(
                                glass.getGlassSpecificationName(),
                                glass.getGlassThicknessName(),
                                decimalFormat.format(itemList.stream()
                                        .filter(g -> g.getGlassSpecificationName().equals(glass.getGlassSpecificationName()) &&
                                                g.getGlassThicknessName().equals(glass.getGlassThicknessName()))
                                        .mapToDouble(item -> Double.parseDouble(decimalFormat.format(item.getUnitValue())))
                                        .sum())
                        )
                ));
        return glassItemDetails;
    }

    public ProFormaInvoiceItemReportValue toReportVal(ProFormaInvoiceItemValue value) {
        return ProFormaInvoiceItemReportValue.newBuilder()
                .id(value.getProFormaInvoiceItemId())
                .uuid(value.getUuid())
                .widthInch(value.getWidthInch())
                .widthMeasurement(value.getWidthMeasurement())
                .widthMeasurementLabel(value.getWidthMeasurementLabel()) //onPrintProformaInvoicePdf onDownloadWorkorderdetails
                .actualWidth(Math.round(value.getActualWidth()) + "")
                .chargeableWidth(value.getChargeableWidth())
                .heightInch(value.getHeightInch())
                .heightMeasurement(value.getHeightMeasurement())
                .heightMeasurementLabel(value.getHeightMeasurementLabel())
                .actualHeight(Math.round(value.getActualHeight()) + "")
                .chargeableHeight(value.getChargeableHeight())
                .extraMm(value.getExtraMm())
                .quantity(value.getQuantity())
                .unitValue(BigDecimal.valueOf(value.getUnitValue()).setScale(2, RoundingMode.HALF_UP))
                .ratePerUnit(value.getRatePerUnit())
                .unitMeasurementLabel(value.getUnitMeasurementLabel())
                .amount(value.getAmount())
                .sqFt(new DecimalFormat("#.00").format(value.getActualHeight() * value.getActualWidth() / 92903 * value.getQuantity()))
                //added below condition to initially inset 0 value in bucket
                .optimizeBucket(value.getOptimizeBucket() == null ? 0 : value.getOptimizeBucket())
                .cuttingBucket(value.getCuttingBucket() == null ? 0 : value.getCuttingBucket())
                .toughenBucket(value.getToughenBucket() == null ? 0 : value.getToughenBucket())
                .dispatchBucket(value.getDispatchBucket() == null ? 0 : value.getDispatchBucket())
                .gatePassBucket(value.getGatePassBucket() == null ? 0 : value.getGatePassBucket())
                .optimizeCompleted(value.getOptimizeCompleted() == null ? 0 : value.getOptimizeCompleted())
                .cuttingCompleted(value.getCuttingCompleted() == null ? 0 : value.getCuttingCompleted())
                .toughenCompleted(value.getToughenCompleted() == null ? 0 : value.getToughenCompleted())
                .dispatchCompleted(value.getDispatchCompleted() == null ? 0 : value.getDispatchCompleted())
                .gatePassCompleted(value.getGatePassCompleted() == null ? 0 : value.getGatePassCompleted())
                .glassSpecificationName(value.getGlassSpecificationName())
                .glassThicknessName(value.getGlassThicknessName())
                .glassTypeName(value.getGlassTypeName())
                //.tenantEntity(getTenantValue().toEntity())
                //.glassTypeEntity(getGlassTypeValue().toEntity())
                //.glassThicknessEntity(getGlassThicknessValue().toEntity())
                //.glassSpecificationEntity(getGlassSpecificationValue().toEntity())
                //.proFormaInvoiceEntity(getProFormaInvoiceValue().toEntity())
                .status(value.getStatus())
                .statusDetails(value.getStatusDetails())
                .isActive(value.getIsActive())
                .build();
    }


}
