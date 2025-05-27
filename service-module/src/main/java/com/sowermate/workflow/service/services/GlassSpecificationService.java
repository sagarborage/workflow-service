package com.sowermate.workflow.service.services;

import com.sowermate.workflow.domain.entities.value.GlassSpecificationValue;

import java.util.List;

public interface GlassSpecificationService {

    public GlassSpecificationValue createGlassSpecification(GlassSpecificationValue glassSpecificationValue);

    public GlassSpecificationValue editGlassSpecification(GlassSpecificationValue glassSpecificationValue);

    public GlassSpecificationValue getGlassSpecification(String tenantUuid, String glassSpecificationUuid);

    public GlassSpecificationValue deleteGlassSpecification(String tenantUuid, String glassSpecificationUuid);

    public List<GlassSpecificationValue> getAllGlassSpecification(String tenantUuid);
}
