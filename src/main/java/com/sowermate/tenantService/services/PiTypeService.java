package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.PiTypeValue;

import java.util.List;

public interface PiTypeService {

    public PiTypeValue createPiType(PiTypeValue piTypeValue) throws Exception;

    public List<PiTypeValue> getAllPiType() throws Exception;
    public PiTypeValue editPiType(PiTypeValue  piTypeValue) throws Exception;

    public PiTypeValue getPiType(String piTypeUuid) throws Exception;

    public PiTypeValue deletePiType(String piTypeUuid)throws Exception;

}
