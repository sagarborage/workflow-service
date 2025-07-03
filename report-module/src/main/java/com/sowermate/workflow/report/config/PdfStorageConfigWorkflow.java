package com.sowermate.workflow.report.config;

import com.sowermate.core.image.configs.PdfStorageConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PdfStorageConfigWorkflow extends PdfStorageConfig {
    @Value("${pdf.upload-directory.product-invoices}")
    private String productInvoicesDirectory;
}
