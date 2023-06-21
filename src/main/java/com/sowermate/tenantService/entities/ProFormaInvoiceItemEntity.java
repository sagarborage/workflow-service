package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pro_forma_invoice_item")
@Getter
@Setter
public class ProFormaInvoiceItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_forma_invoice_item_id", unique = true, nullable = false, updatable = false)
    private int proFormaInvoiceItemId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String proFormaInvoiceItemUuid;

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


}
