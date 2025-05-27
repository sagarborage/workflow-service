package com.sowermate.workflow.domain.entities.value;

import com.sowermate.workflow.domain.entities.ProFormaInvoiceItemEntity;
import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.enums.ProformaInvoiceItemStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ProFormaInvoiceItemValue extends BaseDto {

    private Integer srno;//TODO: remove it later on, it is added as part of mui grid code compatibility
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
    private Float chargeableWidth;
    private Float heightInch;
    private Float heightMeasurement;
    private String heightMeasurementLabel;
    private Float actualHeight;
    private Float chargeableHeight;
    private Float extraMm;
    private Integer quantity;
    private Float unitValue;
    private Double ratePerUnit;
    private String unitMeasurementLabel;
    private Double amount;
    private Integer optimizeBucket;
    private Integer cuttingBucket;
    private Integer toughenBucket;
    private Integer dispatchBucket;
    private Integer gatePassBucket;
    private Integer optimizeCompleted;
    private Integer cuttingCompleted;
    private Integer toughenCompleted;
    private Integer dispatchCompleted;
    private Integer gatePassCompleted;
    private String base64File;
    private String fileUrl;
    private ProformaInvoiceItemStatusEnum status;
    private String statusDetails;

    public ProFormaInvoiceItemEntity toEntity() {
        return ProFormaInvoiceItemEntity.newBuilder()
                //.id(getProFormaInvoiceItemId())
                .uuid(getUuid())
                .widthInch(getWidthInch())
                .toughenBucket(getToughenBucket())
                .widthMeasurement(getWidthMeasurement())
                .widthMeasurementLabel(getWidthMeasurementLabel())
                .actualWidth(getActualWidth())
                .chargeableWidth(getChargeableWidth())
                .heightInch(getHeightInch())
                .heightMeasurement(getHeightMeasurement())
                .heightMeasurementLabel(getHeightMeasurementLabel())
                .actualHeight(getActualHeight())
                .chargeableHeight(getChargeableHeight())
                .extraMm(getExtraMm())
                .quantity(getQuantity())
                .unitValue(getUnitValue())
                .ratePerUnit(getRatePerUnit())
                .unitMeasurementLabel(getUnitMeasurementLabel())
                .amount(getAmount())
                //added below condition to initially inset 0 value in bucket
                .optimizeBucket(getOptimizeBucket() == null ? 0 : getOptimizeBucket())
                .cuttingBucket(getCuttingBucket() == null ? 0 : getCuttingBucket())
                .toughenBucket(getToughenBucket() == null ? 0 : getToughenBucket())
                .dispatchBucket(getDispatchBucket() == null ? 0 : getDispatchBucket())
                .gatePassBucket(getGatePassBucket() == null ? 0 : getGatePassBucket())
                .optimizeCompleted(getOptimizeCompleted() == null ? 0 : getOptimizeCompleted())
                .cuttingCompleted(getCuttingCompleted() == null ? 0 : getCuttingCompleted())
                .toughenCompleted(getToughenCompleted() == null ? 0 : getToughenCompleted())
                .dispatchCompleted(getDispatchCompleted() == null ? 0 : getDispatchCompleted())
                .gatePassCompleted(getGatePassCompleted() == null ? 0 : getGatePassCompleted())
                //.tenantEntity(getTenantValue().toEntity())
                //.glassTypeEntity(getGlassTypeValue().toEntity())
                //.glassThicknessEntity(getGlassThicknessValue().toEntity())
                //.glassSpecificationEntity(getGlassSpecificationValue().toEntity())
                //.proFormaInvoiceEntity(getProFormaInvoiceValue().toEntity())
                .status(getStatus())
                .statusDetails(getStatusDetails())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .version(getVersion())
                .build();
    }
}
