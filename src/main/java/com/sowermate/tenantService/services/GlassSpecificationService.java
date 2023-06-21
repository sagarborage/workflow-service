package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.GlassSpecificationValue;


import java.util.List;

public interface GlassSpecificationService {

    public GlassSpecificationValue createGlassSpecification(GlassSpecificationValue glassSpecificationValue) throws Exception;

    public GlassSpecificationValue editGlassSpecification(GlassSpecificationValue glassSpecificationValue) throws Exception;

    public GlassSpecificationValue getGlassSpecification(String glassSpecificationUuid) throws Exception;

    public GlassSpecificationValue deleteGlassSpecification(String glassSpecificationUuid) throws Exception;

    public List<GlassSpecificationValue> getAllGlassSpecification() throws Exception;
}
