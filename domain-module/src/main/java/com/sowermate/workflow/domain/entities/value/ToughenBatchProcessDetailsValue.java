package com.sowermate.workflow.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.entities.ToughenBatchProcessDetailsEntity;
import com.sowermate.workflow.domain.enums.ToughenBatchProcessStatusEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToughenBatchProcessDetailsValue extends BaseDto {
    private String proFormaInvoiceUuid;
    private String workOrderUuid;
    private String proFormaInvoiceItemUuid;
    private Integer stickerNumber;
    private ToughenBatchProcessStatusEnum status;

    public ToughenBatchProcessDetailsEntity toEntity() {
        return ToughenBatchProcessDetailsEntity.newBuilder()
                .uuid(getUuid())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .version(getVersion())
                .build();
    }
}
