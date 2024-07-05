package com.sowermate.report.controllers;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
public class PIReportHeaderDetails {
    private String glassSpecificationName;
    private String glassThicknessName;
    private String unitTotal;

    public PIReportHeaderDetails(String glassSpecificationName, String glassThicknessName, String unitTotal){
        this.glassSpecificationName = glassSpecificationName;
        this.glassThicknessName = glassThicknessName;
        this.unitTotal = unitTotal;
    }
}
