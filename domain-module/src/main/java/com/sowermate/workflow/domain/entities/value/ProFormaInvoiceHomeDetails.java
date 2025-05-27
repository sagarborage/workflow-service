package com.sowermate.workflow.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.workflow.domain.enums.ProformaInvoiceStatusEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProFormaInvoiceHomeDetails {
    private String uuid;
    private String confirmThroughUuid;
    private String workOrderUuid;
    private Long workOrderNumber;
    private String partyName;
    private String piNumber;
    private Float payableAmount;
    private boolean isGatePassEnabled;
    private boolean isEditAllowed;
    private LocalDateTime invoiceDate;
    private ProformaInvoiceStatusEnum status;
}

