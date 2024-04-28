package com.sowermate.tenantService.entities.minimal;

import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;

import java.time.LocalDate;

public interface ToughenBatchProcessProjection {
    String getBatchUuid();

    String getBatchNo();

    String getProformaInvoiceItemUuid();

    String getProformaInvoiceUuid();

    LocalDate getBatchDate();

    String getPiNo();

    String getBillToPartyName();

    String getBillToPartyUuid();

    String getThickness();

    Double getActualWidth();

    Double getChargeableWidth();

    Double getActualHeight();

    Double getChargeableHeight();

    ToughenBatchProcessStatusEnum getItemStatus();
}
