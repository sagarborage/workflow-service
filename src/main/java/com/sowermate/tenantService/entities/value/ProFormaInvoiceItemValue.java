package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProFormaInvoiceItemValue extends BaseValue {

    private Long proFormaInvoiceItemId;
    private String uuid;
    private String proFormaInvoiceUuid;
    private String glassTypeUuid;
    private String glassTypeName;
    private String glassSpecificationUuid;
    private String glassSpecificationName;
    private String glassThicknessUuid;
    private String glassThicknessName;
    private String tenantUuid;
    private Float widthInch;
    private Float widthMeasurement;
    private String widthMeasurementLabel;
    private Float actualWidth;
    private Float chargableWidth;
    private Float heightInch;
    private Float heightMeasurement;
    private String heightMeasurementLabel;
    private Float actualHight;
    private Float chargableHight;
    private Float extraMm;
    private int quantity;
    private Float unitValue;
    private Double ratePerUnit;
    private String unitMeasurementLabel;
    private Double amount;
    private int optimizeBucket;
    private int cuttingBucket;
    private int toughenBucket;
    private int dispatchBucket;
    private int optimizeCompleted;
    private int cuttingCompleted;
    private int toughenCompleted;
    private int dispatchCompleted;

    public ProFormaInvoiceItemEntity toEntity() {
        return ProFormaInvoiceItemEntity.newBuilder()
                .id(getProFormaInvoiceItemId())
                .uuid(getUuid())
                .widthInch(getWidthInch())
                .widthMeasurement(getWidthMeasurement())
                .widthMeasurementLabel(getWidthMeasurementLabel())
                .actualWidth(getActualWidth())
                .chargableWidth(getChargableWidth())
                .heightInch(getHeightInch())
                .heightMeasurement(getHeightMeasurement())
                .heightMeasurementLabel(getHeightMeasurementLabel())
                .actualHeight(getActualHight())
                .chargableHeight(getChargableHight())
                .extraMm(getExtraMm())
                .quantity(getQuantity())
                .unitValue(getUnitValue())
                .ratePerUnit(getRatePerUnit())
                .unitMeasurementLabel(getUnitMeasurementLabel())
                .amount(getAmount())
                .optimizeBucket(getOptimizeBucket())
                .cuttingBucket(getCuttingBucket())
                .toughenBucket(getToughenBucket())
                .dispatchBucket(getDispatchBucket())
                .optimizeCompleted(getOptimizeCompleted())
                .cuttingCompleted(getCuttingCompleted())
                .toughenCompleted(getToughenCompleted())
                .dispatchCompleted(getDispatchCompleted())
                //.tenantEntity(getTenantValue().toEntity())
                //.glassTypeEntity(getGlassTypeValue().toEntity())
                //.glassThicknessEntity(getGlassThicknessValue().toEntity())
                //.glassSpecificationEntity(getGlassSpecificationValue().toEntity())
                //.proFormaInvoiceEntity(getProFormaInvoiceValue().toEntity())
                .isActive(isActive())
                .build();
    }
}
