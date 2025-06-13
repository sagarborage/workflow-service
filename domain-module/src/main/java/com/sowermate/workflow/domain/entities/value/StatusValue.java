package com.sowermate.workflow.domain.entities.value;

import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.entities.StatusEntity;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@Data
public class StatusValue extends BaseDto {
    private String tenantUuid;
    private String name;

    public StatusEntity toEntity() {
        return StatusEntity.newBuilder()
                .uuid(getUuid())
                .name(getName())
                .isActive(getIsActive())
                .build();
    }
}
