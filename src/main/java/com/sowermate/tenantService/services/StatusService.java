package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.GlassThicknessValue;
import com.sowermate.tenantService.entities.value.StatusValue;

import java.util.List;

public interface StatusService {

    public StatusValue createStatus(StatusValue statusValue) throws Exception;

    public StatusValue editStatus(StatusValue statusValue) throws Exception;

    public StatusValue getStatus(String uuid) throws Exception;

    public StatusValue deleteStatus(int statusId) throws Exception;

    public List<StatusValue> getAllStatus() throws Exception;
}
