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
    private Float actualHight;

    @Column(name = "chargable_height")
    private Float chargableHight;

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

    @Column(name = "`optimize`")
    private int optimize;

    @Column(name = "`cutting`")
    private int cutting;

    @Column(name = "`toughen`")
    private int toughen;

    @Column(name = "`dispatch`")
    private int dispatch;

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
                .uuid(getUuid())
                .widthInch(getWidthInch())
                .widthMeasurement(getWidthMeasurement())
                .widthMeasurementLabel(getWidthMeasurementLabel())
                .actualWidth(getActualWidth())
                .chargableWidth(getChargableWidth())
                .heightInch(getHeightInch())
                .heightMeasurement(getHeightMeasurement())
                .heightMeasurementLabel(getHeightMeasurementLabel())
                .actualHight(getActualHight())
                .chargableHight(getChargableHight())
                .extraMm(getExtraMm())
                .quantity(getQuantity())
                .unitValue(getUnitValue())
                .ratePerUnit(getRatePerUnit())
                .unitMeasurementLabel(getUnitMeasurementLabel())
                .amount(getAmount())
                .optimize(getOptimize())
                .cutting(getCutting())
                .toughen(getToughen())
                .dispatch(getDispatch())
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
