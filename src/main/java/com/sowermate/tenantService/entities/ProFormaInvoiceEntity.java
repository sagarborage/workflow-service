package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "pro_forma_invoice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ProFormaInvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer proFormaInvoiceId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String proFormInvoiceUuid;

    @Column(name = "pi_number")
    private int piNumber;

    @Column(name = "invoice_date")
    private Date invoiceDate;

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

    @Column(name = "urgency_percent")
    private Float urgencyPercent;

    @Column(name = "urgency_percent_amount")
    private Float urgencyPercentAmount;

    @Column(name = "other_charges")
    private Double otherCharges;

    @Column(name = "transport_charges")
    private Double transportCharges;

    @Column(name = "gst_charges")
    private Float gstCharges;

    @Column(name = "grand_total")
    private Float grandTotal;


    @Column(name = "round_off_amount")
    private int roundOffAmount;

    @Column(name = "payable_amount")
    private Float payableAmount;

    @Column(name = "previous_balance")
    private Float previousBalance;

    @Column(name = "adjustment_amount")
    private int adjustmentAmount;

    @Column(name = "status")
    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirm_through_id")
    private ConfirmThroughEntity confirmThroughEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pi_type_id")
    private PiTypeEntity piTypeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bill_to")
    private  CompanyEntity companyIdBill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ship_to")
    private  CompanyEntity companyIdShip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    @OneToMany(mappedBy="proFormaInvoiceEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities;

    @OneToMany(mappedBy="proFormaInvoiceEntity",cascade=CascadeType.ALL)
    private List<ServiceRateInvoiceEntity> serviceRateInvoiceEntities;

    public ProFormaInvoiceValue toDTO() {
        return ProFormaInvoiceValue.newBuilder()
                .proFormaInvoiceId(getProFormaInvoiceId())
                .proFormInvoiceUuid(getProFormInvoiceUuid())
                .piNumber(getPiNumber())
                .invoiceDate(getInvoiceDate())
                .proFormaInvoiceAmount(getProFormaInvoiceAmount())
                .serviceRateInvoiceAmount(getServiceRateInvoiceAmount())
                .basicAmount(getBasicAmount())
                .adminCharges(getAdminCharges())
                .insurancePercent(getInsurancePercent())
                .insurancePercentAmount(getInsurancePercentAmount())
                .urgencyPercent(getUrgencyPercent())
                .urgencyPercentAmount(getUrgencyPercentAmount())
                .otherCharges(getOtherCharges())
                .transportCharges(getTransportCharges())
                .gstCharges(getGstCharges())
                .grandTotal(getGrandTotal())
                .roundOffAmount(getRoundOffAmount())
                .payableAmount(getPayableAmount())
                .previousBalance(getPreviousBalance())
                .adjustmentAmount(getAdjustmentAmount())
                .status(getStatus())
                .proFormaInvoiceItems(getProFormaInvoiceItemEntities().stream().map(entity -> {
                   return entity.toDTO();
                }).collect(Collectors.toList()))
                .build();
    }
}
