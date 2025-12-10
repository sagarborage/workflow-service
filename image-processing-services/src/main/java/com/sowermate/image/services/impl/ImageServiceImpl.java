package com.sowermate.image.services.impl;


import com.sowermate.image.config.ImageStorageConfig;
import com.sowermate.image.constants.ImageExtensionConstants;
import com.sowermate.image.constants.ImageFileTypeConstants;
import com.sowermate.image.services.ImageService;
import com.sowermate.image.services.PdfService;
import com.sowermate.image.utils.FileDetection;
import com.sowermate.image.utils.TypeDetection;
import net.coobird.thumbnailator.Thumbnails;
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

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageStorageConfig imageStorageConfig;
    @Autowired
    private PdfService pdfService;

    public String saveImage(String base64Image, String userProfileUuid, String serviceType, String targetDirectory) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        if (FileDetection.fileDetect(base64Image)) {
            return handleImage(imageBytes, userProfileUuid, serviceType, targetDirectory);
        } else {
            return pdfService.handlePdf(imageBytes, userProfileUuid, serviceType, targetDirectory);
        }
    }

    private String handleImage(byte[] imageBytes, String userProfileUuid, String serviceType, String targetDirectory) {
        try {
            Tika tika = new Tika();
            String mimeType = tika.detect(imageBytes);

            String extension = null;
            if (mimeType != null) {
                switch (mimeType) {
                    case ImageFileTypeConstants.PNG:
                        extension = ImageExtensionConstants.PNG;
                        break;
                    case ImageFileTypeConstants.JPG:
                        extension = ImageExtensionConstants.JPG;
                        break;
                    case ImageFileTypeConstants.GIF:
                        extension = ImageExtensionConstants.GIF;
                        break;
                    case ImageFileTypeConstants.BMP:
                        extension = ImageExtensionConstants.BMP;
                        break;
                    case ImageFileTypeConstants.TIFF:
                        extension = ImageExtensionConstants.TIFF;
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

            File baseDirectory = new File(imageStorageConfig.getImageUploadDirectory());
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

            String imageUrl = imageStorageConfig.getImagePublicUrl() + targetDirectory + "/" + userProfileUuid + "/" + filename;
            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error handling the Image.";
        }
    }

    @Override
    public String generateImage(String image, Integer width, Integer height, Boolean isCropped, String type) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(image);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(inputStream)
                .size(width, height)
                .toOutputStream(outputStream);
        byte[] resizedImageBytes = outputStream.toByteArray();

        String resizedBase64Image = Base64.getEncoder().encodeToString(resizedImageBytes);
        return resizedBase64Image;
    }

}
