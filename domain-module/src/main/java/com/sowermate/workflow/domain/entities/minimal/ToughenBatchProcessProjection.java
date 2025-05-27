package com.sowermate.workflow.domain.entities.minimal;

import com.sowermate.workflow.domain.enums.ItemTypeEnum;
import com.sowermate.workflow.domain.enums.ToughenBatchProcessStatusEnum;

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
