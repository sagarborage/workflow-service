package com.sowermate.workflow.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.entities.DeptTypeEnum;
import com.sowermate.workflow.domain.entities.GlassBreakageDetailsEntity;
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
    private DeptTypeEnum deptName;
    private String details;

    public GlassBreakageDetailsEntity toEntity() {
        return GlassBreakageDetailsEntity.newBuilder()
                .uuid(getUuid())
                .deptName(getDeptName())
                .details(getDetails())
                .isActive(getIsActive())
                .build();
    }
}
