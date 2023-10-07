package com.sowermate.tenantService.entities.minimal;

import java.time.LocalDateTime;

public interface ProFormaInvoiceMinimal {
    String getUuid();

    String getpartyName();

    String getPiNumber();

    Float getPayableAmount();

    LocalDateTime getInvoiceDate();

    String getStatus();
}
