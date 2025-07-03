package com.sowermate.workflow.report.dtos;

import com.sowermate.core.tenant.projections.TenantInfoProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PIReportAddressDto {
    TenantInfoProjection billTo;
    TenantInfoProjection shipTo;
}
