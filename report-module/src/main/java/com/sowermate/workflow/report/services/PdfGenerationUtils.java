package com.sowermate.workflow.report.services;


import com.sowermate.core.tenant.projections.TenantInfoProjection;

import java.util.HashMap;
import java.util.Map;

public class PdfGenerationUtils {

    public static String extractedAddressInfo(TenantInfoProjection address) {
        return address != null ? address.getAddressLine1() + ", " + getStateName(address.getStateCode()) + ", " + address.getPinCode()
                + "<br/>" +
                "<b>Ph:</b> " + address.getPrimaryPhoneNumber() + ", <b>E-mail:</b> " + address.getEmail()
                + "<br/>" +
                "<b>GST No:</b> " + address.getGstNumber() + ", <b>PAN:</b> " + address.getPanNumber() + ", <b>UDYAM No:</b> " + address.getAddressLine1()
                + "<br/>" +
                "<b>TAN No:</b> " + address.getTanNumber() : "";
    }

    public static String getStateName(String stateKey) {
        Map<String, String> stateMap = new HashMap<>();
        stateMap.put("AN", "ANDAMAN AND NICOBAR ISLANDS");
        stateMap.put("AP", "ANDHRA PRADESH");
        stateMap.put("AR", "ARUNACHAL PRADESH");
        stateMap.put("AS", "ASSAM");
        stateMap.put("BR", "BIHAR");
        stateMap.put("CH", "CHANDIGARH");
        stateMap.put("CG", "CHATTISGARH");
        stateMap.put("DN", "DADRA AND NAGAR HAVELI");
        stateMap.put("DD", "DAMAN AND DIU");
        stateMap.put("DL", "DELHI");
        stateMap.put("GA", "GOA");
        stateMap.put("GJ", "GUJARAT");
        stateMap.put("HR", "HARYANA");
        stateMap.put("HP", "HIMACHAL PRADESH");
        stateMap.put("JK", "JAMMU AND KASHMIR");
        stateMap.put("JH", "JHARKHAND");
        stateMap.put("KA", "KARNATAKA");
        stateMap.put("KL", "KERALA");
        stateMap.put("LD", "LAKSHADWEEP ISLANDS");
        stateMap.put("MP", "MADHYA PRADESH");
        stateMap.put("MH", "MAHARASHTRA");
        stateMap.put("MN", "MANIPUR");
        stateMap.put("ML", "MEGHALAYA");
        stateMap.put("MZ", "MIZORAM");
        stateMap.put("NL", "NAGALAND");
        stateMap.put("OD", "ODISHA");
        stateMap.put("PY", "PONDICHERRY");
        stateMap.put("PB", "PUNJAB");
        stateMap.put("RJ", "RAJASTHAN");
        stateMap.put("SK", "SIKKIM");
        stateMap.put("TN", "TAMIL NADU");
        stateMap.put("TS", "TELANGANA");
        stateMap.put("TR", "TRIPURA");
        stateMap.put("UP", "UTTAR PRADESH");
        stateMap.put("UK", "UTTARAKHAND");
        stateMap.put("WB", "WEST BENGAL");
        return stateMap.get(stateKey);
    }
}
