package com.sowermate.report.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class DateInfo {
    private String format;
    private String value;

    public String getFormattedValue() {
        if (value != null && format != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(value, formatter).format(formatter);
        }
        return null;
    }

    public void setWithInferredFormat(String value) {
        this.value = value;
        this.format = inferDateFormat(value);
    }

    private String inferDateFormat(String value) {
        List<String> commonDateFormats = Arrays.asList(
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm:ss.SSS",
                "yyyy/MM/dd HH:mm:ss",
                "dd-MM-yyyy HH:mm:ss",
                "MM/dd/yyyy HH:mm:ss",
                "yyyy-MM-dd",
                "MM/dd/yyyy",
                "dd/MM/yyyy",
                "dd-MM-yyyy",
                "yyyy/MM/dd",
                "EEE, dd MMM yyyy",
                "EEE, dd MMM yyyy HH:mm:ss z"

        );

        for (String format : commonDateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDateTime.parse(value, formatter);
                return format;
            } catch (Exception e) {

            }
        }
        return "yyyy-MM-dd'T'HH:mm:ss";
    }
}