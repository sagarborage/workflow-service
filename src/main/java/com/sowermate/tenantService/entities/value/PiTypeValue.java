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
public class PiTypeValue extends BaseValue {

    private String piTypeName;
    private String tenantUuid;

    private List<ProFormaInvoiceValue> proFormaInvoices;

    public PiTypeEntity toEntity() {
        return PiTypeEntity.newBuilder()
                .uuid(getUuid())
                .piTypeName(getPiTypeName())
                .isActive(getIsActive())
                .build();
    }
}
