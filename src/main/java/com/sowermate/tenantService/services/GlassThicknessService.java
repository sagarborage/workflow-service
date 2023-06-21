package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import com.sowermate.tenantService.entities.value.GlassThicknessValue;

import java.util.List;

public interface GlassThicknessService {
    public GlassThicknessValue createGlassThickness(GlassThicknessValue glassThicknessValue) throws Exception;

    public GlassThicknessValue editGlassThickness(GlassThicknessValue glassThicknessValue) throws Exception;

    public GlassThicknessValue getGlassThickness(String glassThicknessUuid) throws Exception;

    public GlassThicknessValue deleteGlassThickness(String glassThicknessUuid) throws Exception;

    public List<GlassThicknessValue> getAllGlassThickness() throws Exception;

}
