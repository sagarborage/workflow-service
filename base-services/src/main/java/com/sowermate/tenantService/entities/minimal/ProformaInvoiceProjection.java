package com.sowermate.tenantService.entities.minimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ProformaInvoiceProjection {
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


}
