package com.sowermate.workflow.domain.entities.minimal;

public interface CompletedGlassesProjection {
    Long getId();

    String getThickness();

    Integer getDispatchCompleted();

    Integer getTotalQuantity();

    Double getUnitValue();
}
