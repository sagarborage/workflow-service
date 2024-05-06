package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
import com.sowermate.tenantService.entities.JbCreationEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JbCreationValue extends BaseDto {

    private Long jbCreationId;
    protected String jbCreationUuid;
    private String toughenBatchProcessUuid;
    private String partyName;
    private Float widthMm;
    private Float heightMm;
    private Integer quantity;
    private String thickness;
    private String status;

    public JbCreationEntity toEntity() {
        return JbCreationEntity.newBuilder()
                .id(getJbCreationId())
                .uuid(getJbCreationUuid())
                .partyName(getPartyName())
                .widthMm(getWidthMm())
                .heightMm(getHeightMm())
                .quantity(getQuantity())
                .thickness(getThickness())
                .status(getStatus())
                .isActive(getIsActive())
                .build();
    }
}
