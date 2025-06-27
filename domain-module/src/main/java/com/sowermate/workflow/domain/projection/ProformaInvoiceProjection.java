package com.sowermate.workflow.domain.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ProformaInvoiceProjection {
    String getUuid();

    String getTenantUuid();

    String getFirm();

    String getCompanyUuid();

    String getPiNumber();

    String getPartyName();

    @JsonIgnore
    LocalDateTime getInvoiceDateTime();

    LocalDate getInvoiceDate();

    String getAmount();

    String getUser();

    String getStatus();
}
