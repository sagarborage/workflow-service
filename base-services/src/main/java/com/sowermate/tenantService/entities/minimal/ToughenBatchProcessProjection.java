package com.sowermate.tenantService.entities.minimal;

import com.sowermate.tenantService.enums.ItemTypeEnum;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;

import java.time.LocalDate;

public interface ToughenBatchProcessProjection {
    String getBatchItemUuid();

    String getBatchUuid();

    Long getWorkOrderNo();

    String getBatchNo();

    ItemTypeEnum getItemType();

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
