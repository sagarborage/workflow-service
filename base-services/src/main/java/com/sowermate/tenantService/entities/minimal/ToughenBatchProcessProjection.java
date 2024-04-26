package com.sowermate.tenantService.entities.minimal;

import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;

import java.time.LocalDate;

public interface ToughenBatchProcessProjection {
    String getBatchUuid();

    String getBatchNo();

    String getProformaInvoiceItemUuidUuid();

    LocalDate getBatchDate();

    String piNo();

    String getBillToPartyName();

    String getBillToPartyUuid();

    Double getActualWidth();

    Double getChargeableWidth();

    Double getActualHeight();

    Double getChargeableHeight();

    ToughenBatchProcessStatusEnum getItemStatus();
}
