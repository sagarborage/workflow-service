package com.sowermate.report.dtos;

import com.sowermate.tenantService.entities.minimal.CompanyInfoProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PIReportAddressDto {
    CompanyInfoProjection billTo;
    CompanyInfoProjection shipTo;
}
