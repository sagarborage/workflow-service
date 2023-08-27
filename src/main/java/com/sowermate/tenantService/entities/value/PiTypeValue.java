package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.PiTypeEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PiTypeValue {

    private Integer piTypeId;
    private String piTypeUuid;
    private String piTypeName;

    private List<ProFormaInvoiceValue> proFormaInvoices;

    private TenantValue tenantValue;

    public PiTypeEntity toEntity() {
        return PiTypeEntity.newBuilder()
                .piTypeId(getPiTypeId())
                .piTypeUuid(getPiTypeUuid())
                .piTypeName(getPiTypeName())
                .tenantEntity(getTenantValue().toEntity())
                .build();
    }
}
