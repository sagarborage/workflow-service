package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
import com.sowermate.tenantService.entities.AddressTypeEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressTypeValue extends BaseDto {

    private Long addressTypeId;
    private String addressTypeUuid;
    private String tenantUuid;
    private String type;
    private String description;

    public AddressTypeEntity toEntity() {
        return AddressTypeEntity.newBuilder()
                .id(getAddressTypeId())
                .uuid(getAddressTypeUuid())
                .type(getType())
                .description(getDescription())
                .isActive(getIsActive())
                //.tenantEntity(getTenantValue().toEntity())
                .build();
    }
}
