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

    private Long glassThicknessId;
    private String glassThicknessUuid;
    private String tenantUuid;
    private String name;
    private boolean isActive;
    private List<ProFormaInvoiceItemValue> proFormaInvoiceItems;


    public GlassThicknessEntity toEntity() {
        return GlassThicknessEntity.newBuilder()
                .id(getGlassThicknessId())
                .uuid(getGlassThicknessUuid())
                .name(getName())
                .isActive(isActive())
                //.proFormaInvoiceItemEntities(getProFormaInvoiceItems().stream().map(i->i.toEntity()).collect(Collectors.toList()))
                .build();
    }
}
