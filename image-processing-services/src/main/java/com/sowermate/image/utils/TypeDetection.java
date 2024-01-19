package com.sowermate.image.utils;

import org.modelmapper.internal.Pair;

public class TypeDetection {

    public static Pair<String, String> getPrefixAndSuffix(String serviceType) {
        String[] parts = serviceType.split("-");
        String pre = parts.length > 0 ? parts[0] : "";
        String suf = parts.length > 1 ? parts[1] : "";

        if (parts.length == 1) {
            suf = pre;
            pre = "";
        }

        String prefix, suffix;

        switch (pre) {
            case "aadhar" -> prefix = "aadhar-";
            case "pan" -> prefix = "pan-";
            case "passport" -> prefix = "passport-";
            case "voter" -> prefix = "voter-";
            case "driving" -> prefix = "driving-";
            default -> prefix = "";
        }

        switch (suf) {
            case "front" -> suffix = "-front";
            case "back" -> suffix = "-back";
            case "t" -> suffix = "-t";
            case "i" -> suffix = "-i";
            case "l" -> suffix = "-l";
            case "z" -> suffix = "-z";
            case "o" -> suffix = "-o";
            default -> suffix = "";
        }
        return Pair.of(prefix, suffix);

    }
}
