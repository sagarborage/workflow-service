package com.sowermate.tenantService.repositories.Utlity;

import java.util.UUID;

public class CommonUtils {


    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
