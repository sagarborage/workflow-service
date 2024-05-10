package com.sowermate.tenantService.entities.minimal;

import com.sowermate.tenantService.enums.ProformaInvoiceItemStatusEnum;

public interface ProFormaInvoiceIndividualsOrdersProjection {
    String getTenantUuid();
    String getCompanyUuid();

    String getProformaInvoiceUuid();

    String getProformaInvoiceItemUuid();

    String getWorkOrderUuid();

    String getPiNumber();

    Long getWorkOrderNo();

    Float getActualHeight();

    Float getActualWidth();

    String getGlassThickness();

    String getGlassType();

    Long getQuantity();

    Long getBucketQuantity();

    Long getCompletedQuantity();
    ProformaInvoiceItemStatusEnum getStatus();
    String getStatusDetails();

}
