package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.enums.ProformaInvoiceItemStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

import java.util.List;

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

    @Column(name = "chargeable_width")
    private Float chargeableWidth;

    @Column(name = "height_inch")
    private Float heightInch;

    @Column(name = "height_measurement")
    private Float heightMeasurement;

    @Column(name = "height_measurement_label")
    private String heightMeasurementLabel;

    @Column(name = "actual_height")
    private Float actualHeight;

    @Column(name = "chargeable_height")
    private Float chargeableHeight;

    @Column(name = "extra_mm")
    private Float extraMm;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "proxQty")
    private Integer proxQty;

    @Column(name = "unit_value")
    private Float unitValue;

    @Column(name = "rate_per_unit")
    private Double ratePerUnit;

    @Column(name = "unit_measurement_label")
    private String unitMeasurementLabel;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "`optimize_bucket`")
    private Integer optimizeBucket;

    @Column(name = "`cutting_bucket`")
    private Integer cuttingBucket;

    @Column(name = "`toughen_bucket`")
    private Integer toughenBucket;

    @Column(name = "`dispatch_bucket`")
    private Integer dispatchBucket;

    @Column(name = "`gate_pass_bucket`")
    private Integer gatePassBucket;


    @Column(name = "`optimize_completed`")
    private Integer optimizeCompleted;

    @Column(name = "`cutting_completed`")
    private Integer cuttingCompleted;

    @Column(name = "`toughen_completed`")
    private Integer toughenCompleted;

    @Column(name = "`dispatch_completed`")
    private Integer dispatchCompleted;

    @Column(name = "`gate_pass_completed`")
    private Integer gatePassCompleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProformaInvoiceItemStatusEnum status;

    @Column(name = "status_details")
    private String statusDetails;

    @Column(name = "file_url")
    private String fileUrl;

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

    @OneToMany(mappedBy = "proFormaInvoiceItemEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GlassBreakageDetailsEntity> glassBreakageDetailsEntities;

    @OneToMany(mappedBy = "proFormaInvoiceItemEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GatePassDetailsEntity> GatePassDetailsEntity;

    @OneToMany(mappedBy="proFormaInvoiceItemEntity",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ToughenBatchProcessDetailsEntity> toughenBatchProcessDetailsEntity;


    public ProFormaInvoiceItemValue toDTO() {
        return ProFormaInvoiceItemValue.newBuilder()
                .proFormaInvoiceUuid(getProFormaInvoiceEntity().getUuid())
                .uuid(getUuid())
                .widthInch(getWidthInch())
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
                .proxQty(getProxQty())
                .unitValue(getUnitValue())
                .ratePerUnit(getRatePerUnit())
                .unitMeasurementLabel(getUnitMeasurementLabel())
                .amount(getAmount())
                .optimizeBucket(getOptimizeBucket())
                .cuttingBucket(getCuttingBucket())
                .toughenBucket(getToughenBucket())
                .dispatchBucket(getDispatchBucket())
                .gatePassBucket(getGatePassBucket())
                .optimizeCompleted(getOptimizeCompleted())
                .cuttingCompleted(getCuttingCompleted())
                .toughenCompleted(getToughenCompleted())
                .dispatchCompleted(getDispatchCompleted())
                .gatePassCompleted(getGatePassCompleted())
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
                .fileUrl(getFileUrl())
                .status(getStatus())
                .statusDetails(getStatusDetails())
                .build();
    }
}
