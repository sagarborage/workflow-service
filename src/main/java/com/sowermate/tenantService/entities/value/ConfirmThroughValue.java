package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.ConfirmThroughEntity;
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
public class ConfirmThroughValue {
    private Integer confirmThroughId;
    private String confirmThroughUuid;
    private String name;

    private TenantValue tenantValue;
    private ProFormaInvoiceValue proFormaInvoice;

    public ConfirmThroughEntity toEntity() {
        return ConfirmThroughEntity.newBuilder()
                .confirmThroughId(getConfirmThroughId())
                .confirmThroughUuid(getConfirmThroughUuid())
                .name(getName())
                .tenantEntity(getTenantValue().toEntity())
                .proFormaInvoiceEntity(getProFormaInvoice().toEntity())
                .build();
    }
}
