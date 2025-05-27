package com.sowermate.workflow.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralParamValue {
    Integer batchNo;
    Integer workOrderNo;
    String tenantUuid;
    String companyUuid;
    String piUuid;
    String workOrderUuid;
    String piItemUuid;
    String batchItemUuid;
    String details;
    Integer stickerNumber;
}
