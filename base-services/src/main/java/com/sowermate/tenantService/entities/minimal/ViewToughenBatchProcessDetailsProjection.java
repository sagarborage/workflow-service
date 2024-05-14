package com.sowermate.tenantService.entities.minimal;

import java.time.LocalDate;

public interface ViewToughenBatchProcessDetailsProjection {

    String getBatchUuid();
    String getBatchNo();
    LocalDate getBatchDate();
    String getPiNo();
    String getBillToPartyName();
    Double getActualWidth();
    Double getActualHeight();
    Double getChargeableWidth();
    Double getChargeableHeight();
    String getThickness();
}
