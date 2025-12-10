package com.sowermate.image.services;

import java.io.IOException;
import java.util.List;

public interface PdfService {
    String handlePdf(byte[] imageBytes, String userProfileUuid, String serviceType, String targetDirectory);

    Boolean deleteFile(String targetDirectory, String parentDirectory, String fileName);

    String mergePDFs(List<String> base64Pdfs) throws IOException;

    boolean deleteFileByUrl(String fileUrl);

    boolean deleteFileAndParentDirectoryByUrl(String fileUrl);

    String getPdfAsBase64(String pdfUrl);
}
