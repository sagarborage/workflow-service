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
public class GlassSpecificationValue {

    private Integer glassSpecificationId;
    private String glassSpecificationUuid;
    private String name;
    private Boolean isActive;
    private TenantValue tenantValue;
    private List<ProFormaInvoiceItemValue> proFormaInvoiceItems;

    public GlassSpecificationEntity toEntity() {
        return GlassSpecificationEntity.newBuilder()
                .glassSpecificationId(getGlassSpecificationId())
                .glassSpecificationUuid(getGlassSpecificationUuid())
                .name(getName())
                .tenantEntity(getTenantValue().toEntity())
                .isActive(getIsActive())
                .build();
    }
}