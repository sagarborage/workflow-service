package com.sowermate.report.services.impl;


import com.sowermate.image.config.PdfStorageConfig;
import com.sowermate.image.services.ImageService;
import com.sowermate.image.services.PdfService;
import com.sowermate.report.services.PdfGenerationService;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

    private byte[] generatePdf(ProFormaInvoiceValue piValue) {
        try {
            Context context = new Context();
            context.setVariable("piValue", piValue);

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
        byte[] pdfBytes = generatePdf(piValue);
      //  EmailRequestDto emailRequestDto = getEmailRequestDto(pdfBytes);
      //  emailRequestService.sendEmailWithTemplateAndAttachment(emailRequestDto);
        return pdfService.handlePdf(pdfBytes, piValue.getProFormaInvoiceUuid(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
    }


}
