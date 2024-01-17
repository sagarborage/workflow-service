package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
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
public class GlassTypeValue extends BaseDto {

    private Long glassTypeId;
    private String tenantUuid;
    private String glassTypeUuid;
    private String glassName;
    private List<ProFormaInvoiceItemValue> proFormaInvoiceItems;

    public GlassTypeEntity toEntity() {
        return GlassTypeEntity.newBuilder()
                .id(getGlassTypeId())
                .uuid(getGlassTypeUuid())
                .glassName(getGlassName())
                .isActive(getIsActive())
                .build();
    }
    
}
