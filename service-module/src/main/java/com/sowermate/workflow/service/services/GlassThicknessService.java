package com.sowermate.workflow.service.services;

import com.sowermate.workflow.domain.entities.value.GlassThicknessValue;

import java.util.List;

public interface GlassThicknessService {
    public GlassThicknessValue createGlassThickness(GlassThicknessValue glassThicknessValue);

    public GlassThicknessValue editGlassThickness(GlassThicknessValue glassThicknessValue);

    public GlassThicknessValue getGlassThicknessNameById(String tenantUuid, String glassThicknessUuid);

    public String getGlassThicknessNameById(Long id);

    public GlassThicknessValue deleteGlassThickness(String tenantUuid, String glassThicknessUuid);

    public List<GlassThicknessValue> getAllGlassThickness(String tenantUuid);
}
