package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.GlassThicknessValue;
import com.sowermate.tenantService.entities.value.StatusValue;

import java.util.List;

public interface StatusService {

    public StatusValue createStatus(StatusValue statusValue);

    public StatusValue editStatus(StatusValue statusValue) throws Exception;

    public StatusValue getStatus(String tenantUuid,String statusUuid);

    public StatusValue deleteStatus(String tenantUuid,String statusUuid);

    public List<StatusValue> getAllStatus(String tenantUuid);
}
