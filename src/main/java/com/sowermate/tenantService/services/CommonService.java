package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.common.CommonEntity;

import java.util.Date;
import java.util.UUID;

public class CommonService {

    protected String getUniqueID() {
        return UUID.randomUUID().toString();
    }

    protected void initCreate(CommonEntity commonEntity) {
        commonEntity.setUuid(getUniqueID());
        commonEntity.setCreatedDttm(new Date());
        commonEntity.setUpdatedDttm(new Date());
    }
    protected void initEdit(CommonEntity commonEntity) {
        commonEntity.setCreatedDttm(new Date());
        commonEntity.setUpdatedDttm(new Date());
    }
}
