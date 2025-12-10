package com.sowermate.image.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PdfStorageConfig {
    @Value("${pdf.upload-directory.base}")
    private String PdfUploadDirectory;
    @Value("${pdf.public-url.base}")
    private String PdfPublicUrl;
    @Value("${pdf.upload-directory.party-docs}")
    private String partyDocsDirectory;
    @Value("${pdf.upload-directory.user-docs}")
    private String userDocsDirectory;
    @Value("${pdf.upload-directory.product-invoices}")
    private String productInvoicesDirectory;
}
