package com.sowermate.tenantService.entities.minimal;

public interface ProFormaInvoiceIndividualsOrdersProjection {

    String getPiNumber();

    Long getWorkOrderNo();

    Float getActualHeight();

    Float getActualWidth();

    String getGlassThickness();

    String getGlassType();

    Long getQuantity();

    Long getBucketQuantity();

    Long completedQuantity();

}
