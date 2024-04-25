package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import com.sowermate.tenantService.entities.GlassBreakageDetailsEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class GlassBreakageDetailsValue extends BaseDto {
    private String tenantUuid;
    private String proFormaInvoiceUuid;
    private String proFormaInvoiceItemUuid;
    private String deptName;
    private String details;

    public GlassBreakageDetailsEntity toEntity() {
        return GlassBreakageDetailsEntity.newBuilder()
                //.id(getId())
                .uuid(getUuid())
                .deptName(getDeptName())
                .details(getDetails())
                .isActive(getIsActive())
                .build();
    }
}
