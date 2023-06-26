package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.GlassTypeValue;

import java.util.List;

public interface GlassTypeService {

    public GlassTypeValue createGlassType(GlassTypeValue glassTypeValue) throws Exception;

    public GlassTypeValue editGlassType(GlassTypeValue glassTypeValue) throws Exception;

    public GlassTypeValue getGlassType(String tenantUuid, String glassTypeUuid) throws Exception;

    public GlassTypeValue deleteGlassType(String tenantUuid, String glassTypeUuid) throws Exception;

    public List<GlassTypeValue> getAllGlassType(String tenantUuid) throws Exception;
}
