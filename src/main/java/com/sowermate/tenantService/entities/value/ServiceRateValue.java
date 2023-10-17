package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.ServiceRateEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRateValue extends BaseValue {

    private String tenantUuid;
    private String name;
    private float rate;
    private List<ServiceRateInvoiceValue> serviceRateInvoices;

    public ServiceRateEntity toEntity() {
        return ServiceRateEntity.newBuilder()
                .uuid(getUuid())
                .name(getName())
                .rate(getRate())
                .isActive(getIsActive())
                //.serviceRateInvoices(getServiceRateInvoices().stream().map(t->t.toEntity()).collect(Collectors.toList()))
                .build();
    }
    
}
