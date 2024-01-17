package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
import com.sowermate.tenantService.entities.RoleTypeEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleTypeValue extends BaseDto {

    private String tenantUuid;
    private String name;

    public RoleTypeEntity toEntity() {
        return RoleTypeEntity.newBuilder()
                //.id(getId())
                .uuid(getUuid())
                .name(getName())
                .isActive(getIsActive())
                .build();
    }
}
