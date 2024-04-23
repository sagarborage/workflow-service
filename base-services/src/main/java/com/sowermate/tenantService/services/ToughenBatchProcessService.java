package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.GeneralParamValue;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessValue;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;

import java.util.List;

public interface ToughenBatchProcessService {

    void toughenBatchProcessItemAdd(GeneralParamValue generalParamValue);

    List<ToughenBatchProcessValue> toughenBatchProcessItemCancel(String tenantUuid, String uuid);

    List<ToughenBatchProcessValue> markToughenBatchProcessComplete(GeneralParamValue generalParamValue);

    List<ToughenBatchProcessValue> getToughenBatchProcessByStatus(ToughenBatchProcessStatusEnum toughenBatchProcessStatusEnum);

}
