package com.sowermate.image.services;

import java.io.IOException;

public interface ImageService {
    String saveImage(String base64Image, String userProfileUuid, String serviceType, String targetDirectory);

    String generateImage(String image, Integer width, Integer height, Boolean isCropped, String type) throws IOException;
}
