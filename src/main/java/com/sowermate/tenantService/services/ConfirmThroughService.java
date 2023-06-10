package com.sowermate.tenantService.services;


import com.sowermate.tenantService.entities.value.ConfirmThroughValue;

import java.util.List;

public interface ConfirmThroughService {
    public ConfirmThroughValue CreateConfirmThrough(ConfirmThroughValue confirmThroughValue) throws Exception;

    public List<ConfirmThroughValue> getAllConfirmThrough() throws Exception;
    public ConfirmThroughValue editConfirmThrough(ConfirmThroughValue  confirmThroughValue) throws Exception;

    public ConfirmThroughValue getConfirmThrough(String uuid) throws Exception;

    public ConfirmThroughValue deleteConfirmThrough(String uuid)throws Exception;

}
