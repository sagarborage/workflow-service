package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
import com.sowermate.tenantService.entities.JbCreationEntity;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JbCreationValue extends BaseDto {

    private String tenantUuid;
    private String firmUuid;
    private Long jbCreationId;
    protected String jbCreationUuid;
    private String toughenBatchProcessUuid;
    private String partyName;
    private Float widthMm;
    private Float heightMm;
    private Integer quantity;
    private String glassThicknessUuid;
    private ToughenBatchProcessStatusEnum status;

    public JbCreationEntity toEntity() {
        return JbCreationEntity.newBuilder()
                .id(getJbCreationId())
                .uuid(getJbCreationUuid())
                .partyName(getPartyName())
                .widthMm(getWidthMm())
                .heightMm(getHeightMm())
                .quantity(getQuantity())
                .status(getStatus())
                .isActive(getIsActive())
                .build();
    }
}
