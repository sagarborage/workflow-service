package com.sowermate.tenantService.services;


import com.sowermate.tenantService.entities.value.ConfirmThroughValue;

import java.util.List;

public interface ConfirmThroughService {
    public ConfirmThroughValue createConfirmThrough(ConfirmThroughValue confirmThroughValue) throws Exception;

    public List<ConfirmThroughValue> getAllConfirmThrough(String tenantUuid) throws Exception;
    public ConfirmThroughValue editConfirmThrough(ConfirmThroughValue  confirmThroughValue) throws Exception;

    public ConfirmThroughValue getConfirmThrough(String tenantUuid, String confirmThroughUuid) throws Exception;

    public ConfirmThroughValue deleteConfirmThrough(String tenantUuid,String confirmThroughUuid)throws Exception;

}
