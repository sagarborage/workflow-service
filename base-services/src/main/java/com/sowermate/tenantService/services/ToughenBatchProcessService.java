package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.ToughenBatchProcessDetailsEntity;
import com.sowermate.tenantService.entities.minimal.ToughenBatchProcessProjection;
import com.sowermate.tenantService.entities.value.GeneralParamValue;
import com.sowermate.tenantService.entities.value.JbCreationValue;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessDetailsValue;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessValue;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;

import java.util.List;

public interface ToughenBatchProcessService {

    void toughenBatchProcessItemAdd(GeneralParamValue generalParamValue);
    void toughenBatchProcessJBAddItems(List<JbCreationValue> jbCreationValues);

    ToughenBatchProcessDetailsValue toughenBatchProcessItemCancel(String uuid, String companyUuid);
    ToughenBatchProcessDetailsValue toughenBatchProcessItemBroke(GeneralParamValue generalParamValue);

    List<ToughenBatchProcessValue> markToughenBatchProcessComplete(GeneralParamValue generalParamValue);

    List<ToughenBatchProcessProjection> getToughenBatchProcessByStatus(String companyUuid, ToughenBatchProcessStatusEnum toughenBatchProcessStatusEnum);

}
