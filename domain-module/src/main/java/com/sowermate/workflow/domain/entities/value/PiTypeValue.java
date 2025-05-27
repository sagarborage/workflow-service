package com.sowermate.workflow.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.entities.PiTypeEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PiTypeValue extends BaseDto {

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
