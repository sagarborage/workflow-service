package com.sowermate.tenantService.entities.minimal;

import java.time.LocalDateTime;

public interface ProFormaInvoiceMinimal {
    String getUuid();

    String getConfirmThroughUuid();

    String getWorkOrderUuid();

    String getworkOrderNumber();

    String getPartyName();

    String getPiNumber();

    Float getPayableAmount();

    LocalDateTime getInvoiceDate();

    String getStatus();
}
