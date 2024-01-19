package com.sowermate.tenantService.entities.common;


import java.time.format.DateTimeFormatter;

public class TimeConstant {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String BEGINNING = " 00:00:00";

    public static final String ENDING = " 23:59:59";
}
