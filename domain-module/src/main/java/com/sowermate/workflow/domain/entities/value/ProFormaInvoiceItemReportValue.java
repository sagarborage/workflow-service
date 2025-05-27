package com.sowermate.workflow.domain.entities.value;

import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.enums.ProformaInvoiceItemStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Setter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ProFormaInvoiceItemReportValue extends BaseDto {
    private Long id;
    private Integer srno;//TODO: remove it later on, it is added as part of mui grid code compatibility
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
    private String actualWidth;//
    private Float chargeableWidth;
    private Float heightInch;
    private Float heightMeasurement;
    private String heightMeasurementLabel;
    private String actualHeight;//
    private Float chargeableHeight;
    private Float extraMm;
    private Integer quantity;
    private BigDecimal unitValue;//
    private String sqFt;//
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
}
