package com.sowermate.workflow.domain.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface GatePassProjection {
    String getTenantUuid();

    String getCompanyUuid();

    String getFirmUuid();

    String getFirmName();

    String getUuid();

    Integer getGatePassNo();

    String getPiNumber();

    String getPartyName();

    String getCompanyName();

    @JsonIgnore
    LocalDateTime getDateTime();

    LocalDate getGetPassDate();

    Integer getQuantity();

    String getVehicleDetails();

    String getCreatedBy();
}
