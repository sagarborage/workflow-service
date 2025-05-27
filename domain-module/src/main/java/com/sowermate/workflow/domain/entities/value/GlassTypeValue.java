package com.sowermate.workflow.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.entities.GlassTypeEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlassTypeValue extends BaseDto {
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
