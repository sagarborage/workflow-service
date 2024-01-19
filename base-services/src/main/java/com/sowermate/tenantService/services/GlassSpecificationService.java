package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.GlassSpecificationValue;


import java.util.List;

public interface GlassSpecificationService {

    public GlassSpecificationValue createGlassSpecification(GlassSpecificationValue glassSpecificationValue);

    public GlassSpecificationValue editGlassSpecification(GlassSpecificationValue glassSpecificationValue);

    public GlassSpecificationValue getGlassSpecification(String tenantUuid, String glassSpecificationUuid);

    public GlassSpecificationValue deleteGlassSpecification(String tenantUuid, String glassSpecificationUuid);

    public List<GlassSpecificationValue> getAllGlassSpecification(String tenantUuid);
}
