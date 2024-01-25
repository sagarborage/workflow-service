package com.sowermate.report.services.impl;


import com.sowermate.image.config.PdfStorageConfig;
import com.sowermate.image.services.ImageService;
import com.sowermate.image.services.PdfService;
import com.sowermate.report.controllers.PIReportHeaderDetails;
import com.sowermate.report.services.PdfGenerationService;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    private byte[] generatePdf(ProFormaInvoiceValue piValue, Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails ) {
        try {
            Context context = new Context();
            context.setVariable("piValue", piValue);
            context.setVariable("glassItemDetails", glassItemDetails);

            String htmlContent = templateEngine.process("proforma-invoice", context);

            ITextRenderer renderer = new ITextRenderer(1000,710);
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
    public String generateInvoice(ProFormaInvoiceValue piValue) throws IOException {
        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails = glassItemDetails(piValue);

        byte[] pdfBytes = generatePdf(piValue, glassItemDetails);
      //  EmailRequestDto emailRequestDto = getEmailRequestDto(pdfBytes);
      //  emailRequestService.sendEmailWithTemplateAndAttachment(emailRequestDto);
        return pdfService.handlePdf(pdfBytes, piValue.getProFormaInvoiceUuid(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
    }

    public Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>>  glassItemDetails(ProFormaInvoiceValue piValue) {
        List<ProFormaInvoiceItemValue> itemList = piValue.getProFormaInvoiceItems();

        Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails = itemList.stream()
                .collect(Collectors.groupingBy(
                        glass -> new PIReportHeaderDetails(
                                glass.getGlassSpecificationName(),
                                glass.getGlassThicknessName(),
                                itemList.stream()
                                        .filter(g -> g.getGlassSpecificationName().equals(glass.getGlassSpecificationName()) &&
                                                g.getGlassThicknessName().equals(glass.getGlassThicknessName()))
                                        .mapToDouble(ProFormaInvoiceItemValue::getUnitValue)
                                        .sum()
                        )
                ));
        return glassItemDetails;
    }

}
