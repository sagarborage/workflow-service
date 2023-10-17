package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.GlassTypeEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlassTypeValue extends BaseValue {

    private String tenantUuid;
    private String glassName;
    private List<ProFormaInvoiceItemValue> proFormaInvoiceItems;

    public GlassTypeEntity toEntity() {
        return GlassTypeEntity.newBuilder()
                .uuid(getUuid())
                .glassName(getGlassName())
                .isActive(getIsActive())
                .build();
    }
    
}
