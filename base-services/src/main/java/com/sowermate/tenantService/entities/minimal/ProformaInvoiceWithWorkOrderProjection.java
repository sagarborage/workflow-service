package com.sowermate.tenantService.entities.minimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ProformaInvoiceWithWorkOrderProjection {

    String getUuid();

    String getTenantUuid();

    String getWorkOrderUuid();

    String getPiNumber();

    String getPiType();

    String getPartyName();

    @JsonIgnore
    LocalDateTime getPIDateTime();

    LocalDate getPIDate();

    @JsonIgnore
    LocalDateTime getWorkOrderDateTime();

    LocalDate getWorkOrderDate();

    String getAmount();

    String getUser();

    Double getSQFT();

    Double getSQMTR();
}
