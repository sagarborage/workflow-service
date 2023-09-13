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
public class ServiceRateInvoiceValue extends BaseValue {

    private String proFormaInvoiceUuid;
    private String serviceRateUuid;
    private int quantity;
    private int rate;
    private int total;

    public ServiceRateInvoiceEntity toEntity() {
        return ServiceRateInvoiceEntity.newBuilder()
                .id(getId())
                .uuid(getUuid())
                .quantity(getQuantity())
                .rate(getRate())
                .total(getTotal())
                //.proFormaInvoiceEntity(getProFormaInvoice().toEntity())
                //.serviceRateEntity(getServiceRate().toEntity())
                .isActive(isActive())
                .build();
    }
}
