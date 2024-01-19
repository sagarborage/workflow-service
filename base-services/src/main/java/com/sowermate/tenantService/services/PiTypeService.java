package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.PiTypeValue;

import java.util.List;

public interface PiTypeService {

    public PiTypeValue createPiType(PiTypeValue piTypeValue);

    public List<PiTypeValue> getAllPiType(String tenantUuid);
    public PiTypeValue editPiType(PiTypeValue  piTypeValue);

    public PiTypeValue getPiType(String tenantUuid,String piTypeUuid);

    public int deletePiType(String tenantUuid,String piTypeUuid);

}
