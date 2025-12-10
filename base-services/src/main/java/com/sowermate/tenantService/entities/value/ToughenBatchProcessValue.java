package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToughenBatchProcessValue extends BaseDto {

    private Long piToughenBatchDetailsId;
    private String tenantUuid;
    private String companyUuid;
    private String proFormaInvoiceUuid;
    private String workOrderUuid;
    private String proFormaInvoiceItemUuid;
    private ToughenBatchProcessStatusEnum status;
    private String statusDetails;
    private List<ToughenBatchProcessDetailsValue> toughenBatchProcessDetailsValues;

    public ToughenBatchProcessEntity toEntity() {
        return ToughenBatchProcessEntity.newBuilder()
                .uuid(getUuid())
                .isActive(getIsActive())
                .build();
    }
}
