package com.sowermate.report.services.impl;


import com.sowermate.image.config.PdfStorageConfig;
import com.sowermate.image.services.ImageService;
import com.sowermate.image.services.PdfService;
import com.sowermate.report.controllers.PIReportHeaderDetails;
import com.sowermate.report.dtos.PIReportDetails;
import com.sowermate.report.services.PdfGenerationService;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfGenerationServiceImpl implements PdfGenerationService {
    @Autowired
    private ImageService imageService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private PdfStorageConfig pdfStorageConfig;
    private final TemplateEngine templateEngine;

    @Autowired
    public PdfGenerationServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    private byte[] generatePdf(ProFormaInvoiceValue piValue,PIReportDetails reportDetails) {
        try {
            Context context = new Context();
            context.setVariable("piValue", piValue);
            context.setVariable("reportDetails", reportDetails);
            /*context.setVariable("glassItemDetails", glassItemDetails);
            context.setVariable("serviceRateDetails", serviceRateDetails);
            context.setVariable("totalQuantity", totalQuantity);
            context.setVariable("totalUnitTotal", totalUnitTotal);
            context.setVariable("totalRatePerUnit", totalRatePerUnit);
            context.setVariable("totalAmount", totalAmount);*/

            String htmlContent = templateEngine.process("proforma-invoice", context);

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

    @Override
    public byte[] generateInvoice(ProFormaInvoiceValue piValue, PIReportDetails reportDetails) throws IOException {
        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails = glassItemDetails(piValue);

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

        reportDetails.setGstType("Maharashtras".equalsIgnoreCase(reportDetails.getBillTo().getState()) ? "SGST-CGST" : "IGST");
        reportDetails.setIGst(""+piValue.getGstCharges());
        reportDetails.setSGst(""+piValue.getGstCharges()/2);
        reportDetails.setCGst(""+piValue.getGstCharges()/2);
        reportDetails.setIPercent((!ObjectUtils.isEmpty(piValue.getInsurancePercent()) && piValue.getInsurancePercent() > 0) ? piValue.getInsurancePercent() +"" : "0");
        reportDetails.setUPercent((!ObjectUtils.isEmpty(piValue.getInsurancePercent()) && piValue.getUrgencyPercent() > 0) ? piValue.getUrgencyPercent() +"" : "0");
        reportDetails.setIPercentAmount((piValue.getInsurancePercentAmount() + ""));
        reportDetails.setUPercentAmount((piValue.getUrgencyPercentAmount() + ""));

        byte[] pdfBytes = generatePdf(piValue, reportDetails);

        //  EmailRequestDto emailRequestDto = getEmailRequestDto(pdfBytes);
        //  emailRequestService.sendEmailWithTemplateAndAttachment(emailRequestDto);
        pdfService.handlePdf(pdfBytes, piValue.getProFormaInvoiceUuid(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
    return pdfBytes;
    }

    private List<ServiceRateInvoiceValue> serviceRateDetails(ProFormaInvoiceValue piValue) {
        return piValue.getServiceRateInvoices();
    }

    public Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails(ProFormaInvoiceValue piValue) {
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
