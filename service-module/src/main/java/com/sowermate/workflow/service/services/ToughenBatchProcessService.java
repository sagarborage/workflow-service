package com.sowermate.workflow.service.services;

import com.sowermate.workflow.domain.entities.minimal.CompletedGlassesProjection;
import com.sowermate.workflow.domain.entities.minimal.StickerReportProjection;
import com.sowermate.workflow.domain.entities.minimal.ToughenBatchProcessProjection;
import com.sowermate.workflow.domain.entities.minimal.ViewToughenBatchProcessDetailsProjection;
import com.sowermate.workflow.domain.entities.value.GeneralParamValue;
import com.sowermate.workflow.domain.entities.value.JbCreationValue;
import com.sowermate.workflow.domain.entities.value.ToughReportDto;
import com.sowermate.workflow.domain.entities.value.ToughenBatchProcessDetailsValue;
import com.sowermate.workflow.domain.entities.value.ToughenBatchProcessValue;
import com.sowermate.workflow.domain.enums.ToughenBatchProcessStatusEnum;

import java.time.LocalDate;
import java.util.List;

public interface ToughenBatchProcessService {
    void toughenBatchProcessItemAdd(GeneralParamValue generalParamValue);

    void toughenBatchProcessJBAddItems(List<JbCreationValue> jbCreationValues);

    ToughenBatchProcessDetailsValue toughenBatchProcessItemCancel(String uuid, String companyUuid);

    ToughenBatchProcessDetailsValue toughenBatchProcessItemBroke(GeneralParamValue generalParamValue);

    List<ToughenBatchProcessValue> markToughenBatchProcessComplete(GeneralParamValue generalParamValue);

    List<ToughenBatchProcessProjection> getToughenBatchProcessByStatus(ToughenBatchProcessStatusEnum toughenBatchProcessStatusEnum);

    List<ViewToughenBatchProcessDetailsProjection> viewToughenBatchProcessDetails(String tenantUuid, LocalDate batchProcessingDate);

    StickerReportProjection getStickerOfBatchItem(String tenantUuid, String companyUuid, String batchItemUuid);

    List<StickerReportProjection> getStickersOfBatch(String tenantUuid, String companyUuid, String batchUuid);

    List<CompletedGlassesProjection> getCompletedGlassesForReport(String tenantUuid, String companyUuid, LocalDate batchItemDate);

    ToughReportDto getToughenReportForReport(LocalDate date);
}
