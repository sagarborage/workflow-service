package com.sowermate.image.services.impl;

import com.sowermate.image.config.PdfStorageConfig;
import com.sowermate.image.constants.FileExtensionConstants;
import com.sowermate.image.constants.FileTypeConstants;
import com.sowermate.image.constants.ImageExtensionConstants;
import com.sowermate.image.services.PdfService;
import com.sowermate.image.utils.TypeDetection;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.tika.Tika;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private PdfStorageConfig pdfStorageConfig;

    @Override
    public String mergePDFs(List<String> base64Pdfs) throws IOException {
        PDFMergerUtility merger = new PDFMergerUtility();

        for (String i : base64Pdfs) {
            byte[] pdfBytes = Base64.getDecoder().decode(i);
            merger.addSource(new ByteArrayInputStream(pdfBytes));
        }
        ByteArrayOutputStream mergedOutput = new ByteArrayOutputStream();
        merger.setDestinationStream(mergedOutput);
        merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

        return Base64.getEncoder().encodeToString(mergedOutput.toByteArray());
    }

    public String handlePdf(byte[] imageBytes, String userProfileUuid, String serviceType, String targetDirectory) {
        try {
            Tika tika = new Tika();
            String mimeType = tika.detect(imageBytes);

            String extension = null;
            if (mimeType != null) {
                switch (mimeType) {
                    case FileTypeConstants.PDF:
                        extension = FileExtensionConstants.PDF;
                        break;
                    case FileTypeConstants.WORD:
                        extension = FileExtensionConstants.WORD;
                        break;
                    case FileTypeConstants.EXCEL:
                        extension = FileExtensionConstants.EXCEL;
                        break;
                    default:
                        extension = ImageExtensionConstants.DAT;
                }
            } else {
                extension = ImageExtensionConstants.DAT;
            }


            Pair<String, String> pair = TypeDetection.getPrefixAndSuffix(serviceType);
            String prefix = pair.getLeft();
            String suffix = pair.getRight();

            File baseDirectory = new File(pdfStorageConfig.getPdfUploadDirectory());
            if (!baseDirectory.exists()) {
                if (baseDirectory.mkdirs()) {
                    System.out.println("Base directory created successfully: " + baseDirectory.getAbsolutePath());
                } else {
                    System.err.println("Failed to create base directory: " + baseDirectory.getAbsolutePath());
                }
            }

            File targetDirectoryFile = new File(baseDirectory, targetDirectory);
            if (!targetDirectoryFile.exists()) {
                if (targetDirectoryFile.mkdirs()) {
                    System.out.println("Target directory created successfully: " + targetDirectoryFile.getAbsolutePath());
                } else {
                    System.err.println("Failed to create target directory: " + targetDirectoryFile.getAbsolutePath());
                }
            }

            File userDirectory = new File(targetDirectoryFile, userProfileUuid);
            if (!userDirectory.exists()) {
                if (userDirectory.mkdir()) {
                    System.out.println("User directory created successfully: " + userDirectory.getAbsolutePath());
                } else {
                    System.err.println("Failed to create user directory: " + userDirectory.getAbsolutePath());
                }
            }

            String filename = prefix + userProfileUuid + suffix + "." + extension;
            File file = new File(userDirectory, filename);

            System.out.println("Writing to file: " + file.getAbsolutePath());
            Files.write(file.toPath(), imageBytes);

            String imageUrl = pdfStorageConfig.getPdfPublicUrl() + targetDirectory + "/" + userProfileUuid + "/" + filename;
            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error handling the PDF.";
        }
    }

    @Override
    public Boolean deleteFile(String targetDirectory, String parentDirectory, String fileName) {
        try {
            File baseDirectory = new File(pdfStorageConfig.getPdfUploadDirectory());
            if (!baseDirectory.exists()) {
                if (baseDirectory.mkdirs()) {
                    System.out.println("Base directory created successfully: " + baseDirectory.getAbsolutePath());
                } else {
                    System.err.println("Failed to create base directory: " + baseDirectory.getAbsolutePath());
                }
            }

            File targetDirectoryFile = new File(baseDirectory, targetDirectory);
            if (!targetDirectoryFile.exists()) {
                if (targetDirectoryFile.mkdirs()) {
                    System.out.println("Target directory created successfully: " + targetDirectoryFile.getAbsolutePath());
                } else {
                    System.err.println("Failed to create target directory: " + targetDirectoryFile.getAbsolutePath());
                }
            }

            File userDirectory = new File(targetDirectoryFile, parentDirectory);
            if (!userDirectory.exists()) {
                if (userDirectory.mkdir()) {
                    System.out.println("User directory created successfully: " + userDirectory.getAbsolutePath());
                } else {
                    System.err.println("Failed to create user directory: " + userDirectory.getAbsolutePath());
                }
            }

            File fileToDelete = new File(userDirectory, fileName);

            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully: " + fileToDelete.getAbsolutePath());
                    return true;
                } else {
                    System.err.println("Failed to delete file: " + fileToDelete.getAbsolutePath());
                    return false;
                }
            } else {
                System.err.println("File not found: " + fileToDelete.getAbsolutePath());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
