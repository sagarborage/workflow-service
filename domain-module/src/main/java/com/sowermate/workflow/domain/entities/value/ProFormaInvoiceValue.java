package com.sowermate.workflow.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.entities.ProFormaInvoiceEntity;
import com.sowermate.workflow.domain.enums.ProformaInvoiceStatusEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProFormaInvoiceValue extends BaseDto {
    private String tenantUuid;
    private String confirmThroughUuid;
    private String confirmThroughName;
    private String piTypeUuid;
    private String piTypeName;
    private String firmUuid;
    private String partyBillToUuid;
    private String partyShipToUuid;
    private String partyBillToName;
    private String partyShipToName;
    private String piNumber;
    private LocalDateTime invoiceDate;
    private Double proFormaInvoiceAmount;
    private Double serviceRateInvoiceAmount;
    private Double basicAmount;
    private Double adminCharges;
    private Float insurancePercent;
    private Float insurancePercentAmount;
    private Float proxSqft;
    private Float proxPerSqftRate;
    private Float proxCharges;
    private Double otherCharges;
    private Double transportCharges;
    private Boolean isGstApplicable;
    private Float gstCharges;
    private Double grandTotal;
    private Float roundOffAmount;
    private Float payableAmount;
    private Float previousBalance;
    private Integer adjustmentAmount;
    private String shippingAddress;
    private ProformaInvoiceStatusEnum status;
    private String statusDetails;

    private List<ProFormaInvoiceItemValue> proFormaInvoiceItems;
    private List<ServiceRateInvoiceValue> serviceRateInvoices;
    private WorkOrderValue workOrderValue;


    public ProFormaInvoiceEntity toEntity() {
        return ProFormaInvoiceEntity.newBuilder()
                .uuid(getUuid())
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
                .shippingAddress(getShippingAddress() == null || getShippingAddress().trim().isEmpty() ? null : getShippingAddress())
                .status(getStatus())
                .statusDetails(getStatusDetails())
                .isActive(getIsActive())
                .createdBy(getCreatedBy())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedBy(getLastUpdatedBy())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .version(getVersion())
                .build();
    }
}
