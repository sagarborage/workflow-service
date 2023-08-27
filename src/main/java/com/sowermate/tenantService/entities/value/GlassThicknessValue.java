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
public class GlassThicknessValue {

    private Integer glassThicknessId;
    private String glassThicknessUuid;
    private String name;
    private Boolean isActive;

    private TenantValue tenantValue;
    private List<ProFormaInvoiceItemValue> proFormaInvoiceItems;


    public GlassThicknessEntity toEntity() {
        return GlassThicknessEntity.newBuilder()
                .glassThicknessId(getGlassThicknessId())
                .glassThicknessUuid(getGlassThicknessUuid())
                .name(getName())
                .isActive(getIsActive())
                .tenantEntity(getTenantValue().toEntity())
                .proFormaInvoiceItemEntities(getProFormaInvoiceItems().stream().map(i->i.toEntity()).collect(Collectors.toList()))
                .build();
    }
}
