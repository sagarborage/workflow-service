package com.sowermate.report.services.impl;


import com.sowermate.image.config.PdfStorageConfig;
import com.sowermate.image.services.ImageService;
import com.sowermate.image.services.PdfService;
import com.sowermate.report.dtos.InvoiceDto;
import com.sowermate.report.services.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

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

    private byte[] generatePdf(InvoiceDto invoiceDto) {
        try {
            Context context = new Context();
            context.setVariable("invoiceDto", invoiceDto);

            String htmlContent = templateEngine.process("flexipunch-invoice", context);

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
    public String generateInvoice(InvoiceDto invoiceDto) throws IOException {
        byte[] pdfBytes = generatePdf(invoiceDto);
      //  EmailRequestDto emailRequestDto = getEmailRequestDto(pdfBytes);
      //  emailRequestService.sendEmailWithTemplateAndAttachment(emailRequestDto);
        return pdfService.handlePdf(pdfBytes, invoiceDto.getInvoiceNumber(), "invoice", pdfStorageConfig.getProductInvoicesDirectory());//TODO: some modification remaining in uuid parameter
    }


}
