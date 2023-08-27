package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.ServiceRateEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRateValue {

    private Integer serviceRateId;
    private String serviceRateUuid;
    private String name;
    private float rate;
    private Boolean isActive;

    private TenantValue tenantValue;

    private List<ServiceRateInvoiceValue> serviceRateInvoices;

    public ServiceRateEntity toEntity() {
        return ServiceRateEntity.newBuilder()
                .serviceRateId(getServiceRateId())
                .serviceRateUuid(getServiceRateUuid())
                .name(getName())
                .rate(getRate())
                .isActive(getIsActive())
                .tenantEntity(getTenantValue().toEntity())
                .serviceRateInvoices(getServiceRateInvoices().stream().map(t->t.toEntity()).collect(Collectors.toList()))
                .build();
    }
    
}
