package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "pro_forma_invoice_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ProFormaInvoiceItemEntity extends Base {

    @Column(name = "width_inch")
    private Float widthInch;

    @Column(name = "width_measurement")
    private Float widthMeasurement;

    @Column(name = "width_measurement_label")
    private String widthMeasurementLabel;

    @Column(name = "actual_width")
    private Float actualWidth;

    @Column(name = "chargable_width")
    private Float chargableWidth;

    @Column(name = "height_inch")
    private Float heightInch;

    @Column(name = "height_measurement")
    private Float heightMeasurement;

    @Column(name = "height_measurement_label")
    private String heightMeasurementLabel;

    @Column(name = "actual_height")
    private Float actualHeight;

    @Column(name = "chargable_height")
    private Float chargableHeight;

    @Column(name = "extra_mm")
    private Float extraMm;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_value")
    private Float unitValue;

    @Column(name = "rate_per_unit")
    private Double ratePerUnit;

    @Column(name = "unit_measurement_label")
    private String unitMeasurementLabel;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "`optimize_bucket`")
    private int optimizeBucket;

    @Column(name = "`cutting_bucket`")
    private int cuttingBucket;

    @Column(name = "`toughen_bucket`")
    private int toughenBucket;

    @Column(name = "`dispatch_bucket`")
    private int dispatchBucket;


    @Column(name = "`optimize_completed`")
    private int optimizeCompleted;

    @Column(name = "`cutting_completed`")
    private int cuttingCompleted;

    @Column(name = "`toughen_completed`")
    private int toughenCompleted;

    @Column(name = "`dispatch_completed`")
    private int dispatchCompleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_forma_invoice_id")
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glass_type_id")
    private GlassTypeEntity glassTypeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glass_specification_id")
    private GlassSpecificationEntity glassSpecificationEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glass_thickness_id")
    private GlassThicknessEntity glassThicknessEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenantEntity;

    public ProFormaInvoiceItemValue toDTO() {
        return ProFormaInvoiceItemValue.newBuilder()
                .proFormaInvoiceItemId(getId())
                .uuid(getUuid())
                .widthInch(getWidthInch())
                .widthMeasurement(getWidthMeasurement())
                .widthMeasurementLabel(getWidthMeasurementLabel())
                .actualWidth(getActualWidth())
                .chargableWidth(getChargableWidth())
                .heightInch(getHeightInch())
                .heightMeasurement(getHeightMeasurement())
                .heightMeasurementLabel(getHeightMeasurementLabel())
                .actualHight(getActualHeight())
                .chargableHight(getChargableHeight())
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
                .glassSpecificationUuid(getGlassSpecificationEntity().getUuid())
                .glassSpecificationName(getGlassSpecificationEntity().getName())
                .glassThicknessUuid(getGlassThicknessEntity().getUuid())
                .glassThicknessName(getGlassThicknessEntity().getName())
                .glassTypeUuid(getGlassTypeEntity().getUuid())
                .glassTypeName(getGlassTypeEntity().getGlassName())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
