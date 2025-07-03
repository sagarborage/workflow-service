package com.sowermate.workflow.report.controllers;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PIReportHeaderDetails {
    private String glassSpecificationName;
    private String glassThicknessName;
    private String unitTotal;

    public PIReportHeaderDetails(String glassSpecificationName, String glassThicknessName, String unitTotal) {
        this.glassSpecificationName = glassSpecificationName;
        this.glassThicknessName = glassThicknessName;
        this.unitTotal = unitTotal;
    }
}
