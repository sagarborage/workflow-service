package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.PiTypeValue;

import java.util.List;

public interface PiTypeService {

    public PiTypeValue createPiType(PiTypeValue piTypeValue) throws Exception;

    public List<PiTypeValue> getAllPiType(String tenantUuid) throws Exception;
    public PiTypeValue editPiType(PiTypeValue  piTypeValue) throws Exception;

    public PiTypeValue getPiType(String tenantUuid,String piTypeUuid) throws Exception;

    public PiTypeValue deletePiType(String tenantUuid,String piTypeUuid)throws Exception;

}
