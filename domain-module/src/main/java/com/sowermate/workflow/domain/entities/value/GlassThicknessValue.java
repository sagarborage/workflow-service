package com.sowermate.workflow.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.entities.GlassThicknessEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlassThicknessValue extends BaseDto {
    private String tenantUuid;
    private String name;
    private List<ProFormaInvoiceItemValue> proFormaInvoiceItems;


    public GlassThicknessEntity toEntity() {
        return GlassThicknessEntity.newBuilder()
                .uuid(getUuid())
                .name(getName())
                .isActive(getIsActive())
                //.proFormaInvoiceItemEntities(getProFormaInvoiceItems().stream().map(i->i.toEntity()).collect(Collectors.toList()))
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .version(getVersion())
                .build();
    }
}
