package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.GlassThicknessValue;

import java.util.List;

public interface GlassThicknessService {
    public GlassThicknessValue createGlassThickness(GlassThicknessValue glassThicknessValue);

    public GlassThicknessValue editGlassThickness(GlassThicknessValue glassThicknessValue);

    public GlassThicknessValue getGlassThickness(String tenantUuid, String glassThicknessUuid);

    public GlassThicknessValue deleteGlassThickness(String tenantUuid, String glassThicknessUuid);

    public List<GlassThicknessValue> getAllGlassThickness(String tenantUuid);

}
