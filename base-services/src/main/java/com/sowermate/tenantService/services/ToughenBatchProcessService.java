package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.ToughenBatchProcessEntity;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessValue;

public interface ToughenBatchProcessService {

    ToughenBatchProcessValue saveOrUpdate(ToughenBatchProcessValue toughenBatchProcessValue);
}
