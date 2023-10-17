package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.GlassThicknessEntity;
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
public class GlassThicknessValue extends BaseValue {

    private String tenantUuid;
    private String name;
    private List<ProFormaInvoiceItemValue> proFormaInvoiceItems;


    public GlassThicknessEntity toEntity() {
        return GlassThicknessEntity.newBuilder()
                .uuid(getUuid())
                .name(getName())
                .isActive(getIsActive())
                .build();
    }
}
