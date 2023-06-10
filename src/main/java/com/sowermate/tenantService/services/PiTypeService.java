package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.PiTypeValue;

import java.util.List;

public interface PiTypeService {

    public PiTypeValue CreatePiType(PiTypeValue piTypeValue) throws Exception;

    public List<PiTypeValue> getAllPiType() throws Exception;
    public PiTypeValue editPiType(PiTypeValue  piTypeValue) throws Exception;

    public PiTypeValue getPiType(String uuid) throws Exception;

    public PiTypeValue deletePiType(String uuid)throws Exception;

}
