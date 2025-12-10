package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.enums.ProformaInvoiceItemStatusEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProformaInvoiceItemReqParam {
    String tenantUuid;
    String companyUuid;
    String piUuid;
    String piItemUuid;
    ProformaInvoiceItemStatusEnum statusFrom;
    ProformaInvoiceItemStatusEnum statusTo;
    String statusDetails;
}
