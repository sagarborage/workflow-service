package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.WorkOrderEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkOrderValue extends BaseValue {
    private String proFormaInvoiceUuid;
    private String firmUuid;
    private String tenantUuid;

    public WorkOrderEntity toEntity() {
        return WorkOrderEntity.newBuilder()
                .uuid(getUuid())
                .isActive(getIsActive())
                .build();
    }
}
