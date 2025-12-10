package com.sowermate.image.utils;


import org.apache.tika.Tika;

import java.util.Base64;

public class FileDetection {
    public static boolean fileDetect(String base64Image) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        Tika tika = new Tika();
        String mimeType = tika.detect(imageBytes);
        if (mimeType.startsWith("image/")) {
            return true;
        } else if (mimeType.startsWith("application/")) {
            return false;
        } else {
            throw new RuntimeException("Unsupported file format!!");

        }
    }
}
