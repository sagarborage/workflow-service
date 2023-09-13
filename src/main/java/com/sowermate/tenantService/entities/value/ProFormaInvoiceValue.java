package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProFormaInvoiceValue extends BaseValue {

    private Long proFormaInvoiceId;
    private String proFormaInvoiceUuid;
    private String tenantUuid;
    private String confirmThroughUuid;
    private String piTypeUuid;
    private String companyBillToUuid;
    private String companyShipToUuid;
    private String piNumber;
    private Date invoiceDate;
    private Double proFormaInvoiceAmount;
    private Double serviceRateInvoiceAmount;
    private Double basicAmount;
    private Double adminCharges;
    private Float insurancePercent;
    private Float insurancePercentAmount;
    private Float urgencyPercent;
    private Float urgencyPercentAmount;
    private Double otherCharges;
    private Double transportCharges;
    private Float gstCharges;
    private Float grandTotal;
    private int roundOffAmount;
    private Float payableAmount;
    private Float previousBalance;
    private int adjustmentAmount;
    private String status;

    private List<ProFormaInvoiceItemValue> proFormaInvoiceItems;
    private List<ServiceRateInvoiceValue> serviceRateInvoices;


    public ProFormaInvoiceEntity toEntity() {
        return ProFormaInvoiceEntity.newBuilder()
                .id(getProFormaInvoiceId())
                .uuid(getProFormaInvoiceUuid())
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
                .build();
    }
}
