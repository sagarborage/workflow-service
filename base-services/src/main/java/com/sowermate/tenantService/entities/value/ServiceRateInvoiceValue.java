package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
import com.sowermate.tenantService.entities.ServiceRateInvoiceEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRateInvoiceValue extends BaseDto {

    private Integer srno;//TODO: remove it later on, it is added as part of mui grid code compatibility
    private String serviceRateName;
    private String proFormaInvoiceUuid;
    private String serviceRateUuid;
    private Integer quantity;
    private Double rate;
    private Double total;

    public ServiceRateInvoiceEntity toEntity() {
        return ServiceRateInvoiceEntity.newBuilder()
                //.id(getId())
                .uuid(getUuid())
                .quantity(getQuantity())
                .rate(getRate())
                .total(getTotal())
                //.proFormaInvoiceEntity(getProFormaInvoice().toEntity())
                //.serviceRateEntity(getServiceRate().toEntity())
                .isActive(getIsActive())
                .build();
    }
}
