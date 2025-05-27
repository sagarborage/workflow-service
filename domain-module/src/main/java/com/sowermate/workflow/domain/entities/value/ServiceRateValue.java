package com.sowermate.workflow.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.entities.ServiceRateEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRateValue extends BaseDto {
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
                .createdBy(getCreatedBy())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedBy(getLastUpdatedBy())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .version(getVersion())
                //.serviceRateInvoices(getServiceRateInvoices().stream().map(t->t.toEntity()).collect(Collectors.toList()))
                .build();
    }
}
