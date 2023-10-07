package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.GlassTypeValue;

import java.util.List;

public interface GlassTypeService {

    public GlassTypeValue createGlassType(GlassTypeValue glassTypeValue);

    public GlassTypeValue editGlassType(GlassTypeValue glassTypeValue);

    public GlassTypeValue getGlassType(String tenantUuid, String glassTypeUuid);

    public GlassTypeValue deleteGlassType(String tenantUuid, String glassTypeUuid);

    public List<GlassTypeValue> getAllGlassType(String tenantUuid);
}
