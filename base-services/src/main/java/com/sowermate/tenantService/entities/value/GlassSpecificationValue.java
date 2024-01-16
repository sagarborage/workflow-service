package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.GlassSpecificationEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlassSpecificationValue extends BaseValue {

    private Long glassSpecificationId;
    private String glassSpecificationUuid;
    private String tenantUuid;
    private String name;
    private List<ProFormaInvoiceItemValue> proFormaInvoiceItems;

    public GlassSpecificationEntity toEntity() {
        return GlassSpecificationEntity.newBuilder()
                .id(getGlassSpecificationId())
                .uuid(getGlassSpecificationUuid())
                .name(getName())
                .isActive(getIsActive())
                .build();
    }
}