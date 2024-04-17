package com.sowermate.image.services;

public interface PdfService {
    String handlePdf(byte[] imageBytes, String userProfileUuid, String serviceType, String targetDirectory);
}
