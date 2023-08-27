package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.ServiceRateInvoiceEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRateInvoiceValue {

    private Integer serviceRateInvoiceId;
    private String serviceRateInvoiceUuid;
    private int quantity;
    private int rate;
    private int total;

    private TenantValue tenantValue;
    private ProFormaInvoiceValue proFormaInvoice;
    private ServiceRateValue serviceRate;

    public ServiceRateInvoiceEntity toEntity() {
        return ServiceRateInvoiceEntity.newBuilder()
                .serviceRateInvoiceId(getServiceRateInvoiceId())
                .serviceRateInvoiceUuid(getServiceRateInvoiceUuid())
                .quantity(getQuantity())
                .rate(getRate())
                .total(getTotal())
                .tenantEntity(getTenantValue().toEntity())
                .proFormaInvoiceEntity(getProFormaInvoice().toEntity())
                .serviceRateEntity(getServiceRate().toEntity())
                .build();
    }
}
