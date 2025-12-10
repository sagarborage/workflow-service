package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.minimal.CompletedGlassesProjection;
import com.sowermate.tenantService.entities.minimal.StickerReportProjection;
import com.sowermate.tenantService.entities.minimal.ToughenBatchProcessProjection;
import com.sowermate.tenantService.entities.minimal.ToughenReportProjection;
import com.sowermate.tenantService.entities.minimal.ViewToughenBatchProcessDetailsProjection;
import com.sowermate.tenantService.entities.value.GeneralParamValue;
import com.sowermate.tenantService.entities.value.JbCreationValue;
import com.sowermate.tenantService.entities.value.ToughReportDto;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessDetailsValue;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessValue;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ToughenBatchProcessService {

    void toughenBatchProcessItemAdd(GeneralParamValue generalParamValue);

    void toughenBatchProcessJBAddItems(List<JbCreationValue> jbCreationValues);

    ToughenBatchProcessDetailsValue toughenBatchProcessItemCancel(String uuid, String companyUuid);

    ToughenBatchProcessDetailsValue toughenBatchProcessItemBroke(GeneralParamValue generalParamValue);

    List<ToughenBatchProcessValue> markToughenBatchProcessComplete(GeneralParamValue generalParamValue);

    List<ToughenBatchProcessProjection> getToughenBatchProcessByStatus(String companyUuid, ToughenBatchProcessStatusEnum toughenBatchProcessStatusEnum);

    List<ViewToughenBatchProcessDetailsProjection> viewToughenBatchProcessDetails(String tenantUuid, String companyUuid, LocalDate batchProcessingDate);

    StickerReportProjection getStickerOfBatchItem(String tenantUuid, String companyUuid, String batchItemUuid);

    List<StickerReportProjection> getStickersOfBatch(String tenantUuid, String companyUuid, String batchUuid);

    List<CompletedGlassesProjection> getCompletedGlassesForReport(String tenantUuid, String companyUuid, LocalDate batchItemDate);

    ToughReportDto getToughenReportForReport(LocalDate date);
}
