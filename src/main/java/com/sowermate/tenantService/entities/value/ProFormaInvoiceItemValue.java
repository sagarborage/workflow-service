package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class ProFormaInvoiceItemValue {

    private String tenantUuid;
    private String ProFormaInvoiceItemUuid;
    private String proFormaInvoiceUuid;
    private String glassTypeUuid;
    private String glassSpecificationUuid;
    private String glassThicknessUuid;
    private Float widthInch;
    private Float widthMeasurement;
    private Float actualWidth;
    private Float chargableWidth;
    private Float hightInch;
    private Float hightMeasurement;
    private Float actualHight;
    private Float chargableHight;
    private Float extraMm;
    private int quantity;
    private Float sqft;
    private Double ratePerSqft;
    private Double amount;
}
