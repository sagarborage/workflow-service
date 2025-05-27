package com.sowermate.workflow.service.services;


import com.sowermate.workflow.domain.entities.value.ConfirmThroughValue;

import java.util.List;

public interface ConfirmThroughService {
    public ConfirmThroughValue createConfirmThrough(ConfirmThroughValue confirmThroughValue);

    public List<ConfirmThroughValue> getAllConfirmThrough(String tenantUuid);

    public ConfirmThroughValue editConfirmThrough(ConfirmThroughValue confirmThroughValue);

    public ConfirmThroughValue getConfirmThrough(String tenantUuid, String confirmThroughUuid);

    public int deleteConfirmThrough(String tenantUuid, String confirmThroughUuid);

}
