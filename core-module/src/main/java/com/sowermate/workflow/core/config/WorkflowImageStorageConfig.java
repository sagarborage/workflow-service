package com.sowermate.workflow.core.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WorkflowImageStorageConfig {
    @Value("${pdf.upload-directory.pro-form-invoice-item}")
    private String proFormInvoicePdfDirectory;
}
