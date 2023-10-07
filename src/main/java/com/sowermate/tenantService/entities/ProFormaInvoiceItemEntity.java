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
public class ProFormaInvoiceItemEntity extends Base{

    @Column(name = "width_inch")
    private Float widthInch;

    @Column(name = "width_measurement")
    private Float widthMeasurement;

    @Column(name = "actual_width")
    private Float actualWidth;

    @Column(name = "chargable_width")
    private Float chargableWidth;

    @Column(name = "hight_inch")
    private Float hightInch;

    @Column(name = "hight_measurement")
    private Float hightMeasurement;

    @Column(name = "actual_hight")
    private Float actualHight;

    @Column(name = "chargable_hight")
    private Float chargableHight;

    @Column(name = "extra_mm")
    private Float extraMm;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "sqft")
    private Float sqft;

    @Column(name = "rate_per_sqft")
    private Double ratePerSqft;

    @Column(name = "amount")
    private Double amount;

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
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public ProFormaInvoiceItemValue toDTO() {
        return ProFormaInvoiceItemValue.newBuilder()
                .proFormaInvoiceItemId(getId())
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
                .glassSpecificationUuid(getGlassSpecificationEntity().getUuid())
                .glassThicknessUuid(getGlassTypeEntity().getUuid())
                .glassTypeUuid(getGlassThicknessEntity().getUuid())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(isActive())
                .build();
    }
}
