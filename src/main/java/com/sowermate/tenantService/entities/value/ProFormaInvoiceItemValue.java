package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProFormaInvoiceItemValue {

    private Integer proFormaInvoiceItemId;
    private String uuid;
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

    private ProFormaInvoiceValue proFormaInvoiceValue;
    private GlassTypeValue glassTypeValue;
    private GlassSpecificationValue glassSpecificationValue;
    private GlassThicknessValue glassThicknessValue;
    private TenantValue tenantValue;

    public ProFormaInvoiceItemEntity toEntity() {
        return ProFormaInvoiceItemEntity.newBuilder()
                .proFormaInvoiceItemId(getProFormaInvoiceItemId())
                .uuid(getUuid())
                .widthInch(getWidthInch())
                .widthMeasurement(getWidthMeasurement())
                .actualWidth(getActualWidth())
                .chargableWidth(getChargableWidth())
                .hightInch(getHightInch())
                .hightMeasurement(getHightMeasurement())
                .actualHight(getActualHight())
                .chargableHight(getChargableHight())
                .extraMm(getExtraMm())
                .quantity(getQuantity())
                .sqft(getSqft())
                .ratePerSqft(getRatePerSqft())
                .amount(getAmount())
                .tenantEntity(getTenantValue().toEntity())
                .glassTypeEntity(getGlassTypeValue().toEntity())
                .glassThicknessEntity(getGlassThicknessValue().toEntity())
                .glassSpecificationEntity(getGlassSpecificationValue().toEntity())
                .proFormaInvoiceEntity(getProFormaInvoiceValue().toEntity())
                .build();
    }
}
