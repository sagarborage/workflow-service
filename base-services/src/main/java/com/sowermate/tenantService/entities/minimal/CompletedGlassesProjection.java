package com.sowermate.tenantService.entities.minimal;

public interface CompletedGlassesProjection {
    Long getId();

    String getThickness();

    Integer getDispatchCompleted();

    Integer getTotalQuantity();

    Double getUnitValue();
}
