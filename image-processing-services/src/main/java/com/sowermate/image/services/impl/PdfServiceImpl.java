package com.sowermate.image.services.impl;

import com.sowermate.image.config.PdfStorageConfig;
import com.sowermate.image.constants.FileExtensionConstants;
import com.sowermate.image.constants.FileTypeConstants;
import com.sowermate.image.constants.ImageExtensionConstants;
import com.sowermate.image.services.PdfService;
import com.sowermate.image.utils.TypeDetection;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.tika.Tika;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
            if (!isPdf(pdfBytes)) {
                merger.addSource(new ByteArrayInputStream(convertImageToPdf(pdfBytes)));
            } else
                merger.addSource(new ByteArrayInputStream(pdfBytes));
        }
        ByteArrayOutputStream mergedOutput = new ByteArrayOutputStream();
        merger.setDestinationStream(mergedOutput);
        merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

        return Base64.getEncoder().encodeToString(mergedOutput.toByteArray());
    }

    public byte[] convertImageToPdf(byte[] imageBytes) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, imageBytes, "image");
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Get page dimensions
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();

            // Get image dimensions
            float imageWidth = pdImage.getWidth();
            float imageHeight = pdImage.getHeight();

            // Calculate the scaling factors to fit the image to the page
            float scaleX = pageWidth / imageWidth;
            float scaleY = pageHeight / imageHeight;

            // Use the smaller scale to keep the image's aspect ratio
            float scale = Math.min(scaleX, scaleY);

            // Calculate new image dimensions
            float newImageWidth = imageWidth * scale;
            float newImageHeight = imageHeight * scale;

            // Center the image on the page
            float offsetX = (pageWidth - newImageWidth) / 2;
            float offsetY = (pageHeight - newImageHeight) / 2;

            contentStream.drawImage(pdImage, offsetX, offsetY, newImageWidth, newImageHeight);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private boolean isPdf(byte[] fileBytes) {
        // Check if the byte array represents a PDF
        return fileBytes.length > 4 && fileBytes[0] == 0x25 && fileBytes[1] == 0x50 && fileBytes[2] == 0x44 && fileBytes[3] == 0x46;
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
                    case FileTypeConstants.JPG:
                        extension = FileExtensionConstants.JPG;
                        break;
                    case FileTypeConstants.PNG:
                        extension = FileExtensionConstants.PNG;
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

    @Override
    public boolean deleteFileByUrl(String fileUrl) {
        try {
            // Parse the URL to extract necessary information
            URI uri = new URI(fileUrl);
            String path = uri.getPath();
            String[] segments = path.split("/");

            // Extract target directory, user profile UUID, and filename
            String targetDirectory = segments[2]; // Assuming the structure is /targetDirectory/userProfileUuid/filename.extension
            String userProfileUuid = segments[3];
            String filename = segments[4];

            // Construct the file path
            File fileToDelete = new File(pdfStorageConfig.getPdfUploadDirectory() + File.separator + targetDirectory + File.separator + userProfileUuid + File.separator + filename);

            // Check if file exists and delete it
            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully: " + fileToDelete.getAbsolutePath());
                    return true;
                } else {
                    System.err.println("Failed to delete file: " + fileToDelete.getAbsolutePath());
                    return false;
                }
            } else {
                System.err.println("File does not exist: " + fileToDelete.getAbsolutePath());
                return false;
            }
        } catch (URISyntaxException e) {
            System.err.println("Invalid file URL: " + fileUrl);
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteFileAndParentDirectoryByUrl(String fileUrl) {
        try {
            // Parse the URL to extract necessary information
            URI uri = new URI(fileUrl);
            String path = uri.getPath();
            String[] segments = path.split("/");

            // Extract target directory and user profile UUID
            String targetDirectory = segments[2]; // Assuming the structure is /targetDirectory/userProfileUuid/filename.extension
            String userProfileUuid = segments[3];

            // Construct the directory path
            File directoryToDelete = new File(pdfStorageConfig.getPdfUploadDirectory() + File.separator + targetDirectory + File.separator + userProfileUuid);

            // Check if directory exists
            if (directoryToDelete.exists() && directoryToDelete.isDirectory()) {
                // List files in the directory
                File[] files = directoryToDelete.listFiles();
                if (files != null) {
                    // Delete each file in the directory
                    for (File file : files) {
                        if (!file.delete()) {
                            System.err.println("Failed to delete file: " + file.getAbsolutePath());
                            return false;
                        }
                    }
                }

                // Delete the directory itself
                if (directoryToDelete.delete()) {
                    System.out.println("Directory deleted successfully: " + directoryToDelete.getAbsolutePath());
                    return true;
                } else {
                    System.err.println("Failed to delete directory: " + directoryToDelete.getAbsolutePath());
                    return false;
                }
            } else {
                System.err.println("Directory does not exist: " + directoryToDelete.getAbsolutePath());
                return false;
            }
        } catch (URISyntaxException e) {
            System.err.println("Invalid file URL: " + fileUrl);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getPdfAsBase64(String pdfUrl) {
        try {
            // Open a connection to the PDF URL
            URL url = new URL(pdfUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the input stream from the connection
            InputStream inputStream = connection.getInputStream();

            // Read the PDF content into a byte array
            byte[] pdfBytes = IOUtils.toByteArray(inputStream);

            // Close the input stream
            inputStream.close();

            // Convert the PDF content to Base64
            byte[] base64Bytes = Base64.getEncoder().encode(pdfBytes);

            // Convert the Base64 bytes to a string
            String base64String = new String(base64Bytes);

            return base64String;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
