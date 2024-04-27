package com.sowermate.image.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ImageStorageConfig {
    @Value("${image.upload-directory.base}")
    private String ImageUploadDirectory;
    @Value("${image.public-url.base}")
    private String ImagePublicUrl;
    @Value("${image.upload-directory.party-docs}")
    private String partyDocsDirectory;
    @Value("${image.upload-directory.user-docs}")
    private String userDocsDirectory;
    @Value("${image.upload-directory.super-category}")
    private String superCategoryDirectory;
    @Value("${image.upload-directory.category}")
    private String categoryDirectory;
    @Value("${image.upload-directory.sub-category}")
    private String subCategoryDirectory;
    @Value("${image.upload-directory.product-images}")
    private String productImagesDirectory;
    @Value("${image.upload-directory.profile-images}")
    private String profileImagesDirectory;
    @Value("${pdf.upload-directory.pro-form-invoice-item}")
    private String proFormInvoicePdfDirectory;
}
