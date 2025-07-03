package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.enums.ProformaInvoiceStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "pro_forma_invoice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ProFormaInvoiceEntity extends Base {

    @Column(name = "creation_type")
    private String creationType;

    @Column(name = "pi_number")
    private String piNumber;

    @Column(name = "invoice_date")
    private LocalDateTime invoiceDate;

    @Column(name = "pro_forma_invoice_amount")
    private Double proFormaInvoiceAmount;

    @Column(name = "service_rate_invoice_amount")
    private Double serviceRateInvoiceAmount;

    @Column(name = "basic_amount")
    private Double basicAmount;

    @Column(name = "admin_charges")
    private Double adminCharges;

    @Column(name = "insurance_percent")
    private Float insurancePercent;

    @Column(name = "insurance_percent_amount")
    private Float insurancePercentAmount;

    @Column(name = "prox_sqft")
    private Float proxSqft;

    @Column(name = "prox_per_sqft_rate")
    private Float proxPerSqftRate;

    @Column(name = "prox_charges")
    private Float proxCharges;

    @Column(name = "other_charges")
    private Double otherCharges;

    @Column(name = "transport_charges")
    private Double transportCharges;

    @Column(name = "is_gst_applicable")
    private Boolean isGstApplicable;

    @Column(name = "gst_charges")
    private Float gstCharges;

    @Column(name = "grand_total")
    private Double grandTotal;


    @Column(name = "round_off_amount")
    private Float roundOffAmount;

    @Column(name = "payable_amount")
    private Float payableAmount;

    @Column(name = "previous_balance")
    private Float previousBalance;

    @Column(name = "adjustment_amount")
    private Integer adjustmentAmount;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProformaInvoiceStatusEnum status;

    @Column(name = "status_details")
    private String statusDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirm_through_id")
    private ConfirmThroughEntity confirmThroughEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pi_type_id")
    private PiTypeEntity piTypeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firm_id")
    private CompanyEntity firm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bill_to")
    private CompanyEntity companyIdBill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ship_to")
    private CompanyEntity companyIdShip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenantEntity;

    @OneToMany(mappedBy = "proFormaInvoiceEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities;

    @OneToMany(mappedBy = "proFormaInvoiceEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceRateInvoiceEntity> serviceRateInvoiceEntities;

    @OneToOne(mappedBy = "proFormaInvoiceEntity", cascade = CascadeType.ALL)
    private WorkOrderEntity workOrderEntity;

    @OneToMany(mappedBy = "proFormaInvoiceEntity", cascade = CascadeType.ALL)
    private List<GlassBreakageDetailsEntity> glassBreakageDetailsEntities;

    @OneToMany(mappedBy = "proFormaInvoiceEntity", cascade = CascadeType.ALL)
    private List<GatePassEntity> gatePassEntities;

    @PostPersist
    void postPersist() {
        if (getProFormaInvoiceItemEntities() != null) {
            getProFormaInvoiceItemEntities().forEach(item -> item.setProFormaInvoiceEntity(this));
        }
    }

    public ProFormaInvoiceValue toDTO() {
        return ProFormaInvoiceValue.newBuilder()
                .proFormaInvoiceUuid(getUuid())
                .tenantUuid(getTenantEntity().getUuid())
                .confirmThroughUuid(null == getConfirmThroughEntity() ? null : getConfirmThroughEntity().getUuid())
                .confirmThroughName(null == getConfirmThroughEntity() ? null : getConfirmThroughEntity().getName())
                .firmUuid(getFirm().getUuid())
                .partyBillToUuid(getCompanyIdBill().getUuid())
                .partyBillToName(getCompanyIdBill().getCompanyName())
                .partyShipToUuid(null != getCompanyIdShip() ? getCompanyIdShip().getUuid() : "")
                .partyShipToName(null != getCompanyIdShip() ? getCompanyIdShip().getCompanyName() : "")
                .piTypeUuid(piTypeEntity != null ? piTypeEntity.getUuid() : null)
                .piTypeName(piTypeEntity != null ? piTypeEntity.getPiTypeName() : null)
                .creationType(getCreationType())
                .piNumber(getPiNumber())
                .invoiceDate(getInvoiceDate())
                .proFormaInvoiceAmount(getProFormaInvoiceAmount())
                .serviceRateInvoiceAmount(getServiceRateInvoiceAmount())
                .basicAmount(getBasicAmount())
                .adminCharges(getAdminCharges())
                .insurancePercent(getInsurancePercent())
                .insurancePercentAmount(getInsurancePercentAmount())
                .proxSqft(getProxSqft())
                .proxPerSqftRate(getProxPerSqftRate())
                .proxCharges(getProxCharges())
                .otherCharges(getOtherCharges())
                .transportCharges(getTransportCharges())
                .isGstApplicable(getIsGstApplicable())
                .gstCharges(getGstCharges())
                .grandTotal(getGrandTotal())
                .roundOffAmount(getRoundOffAmount())
                .payableAmount(getPayableAmount())
                .previousBalance(getPreviousBalance())
                .adjustmentAmount(getAdjustmentAmount())
                .shippingAddress(getShippingAddress())
                .status(getStatus())
                .statusDetails(getStatusDetails())
                .proFormaInvoiceItems(Optional.ofNullable(getProFormaInvoiceItemEntities()).map(e -> e.stream().map(el -> el.toDTO()).collect(Collectors.toList())).orElse(null))
                .workOrderValue(getWorkOrderEntity() != null ? getWorkOrderEntity().toDTO() : null)
                .serviceRateInvoices(Optional.ofNullable(getServiceRateInvoiceEntities()).map(e -> e.stream().map(entity -> entity.toDTO()).collect(Collectors.toList())).orElse(null))
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
