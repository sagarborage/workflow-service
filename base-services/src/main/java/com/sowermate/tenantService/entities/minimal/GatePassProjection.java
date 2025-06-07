package com.sowermate.tenantService.entities.minimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface GatePassProjection {

    String getTenantUuid();

    String getCompanyUuid();

    String getFirmUuid();

    String getGatePassUuid();

    Integer getGatePassNo();

    String getPiNumber();

    String getPartyName();

    String getCompanyName();

    @JsonIgnore
    LocalDateTime getDateTime();

    LocalDate getGetPassDate();

    Integer getQuantity();

    String getVehicleDetails();
}
