package com.sowermate.report.dtos;

import com.sowermate.report.controllers.PIReportHeaderDetails;
import com.sowermate.tenantService.entities.minimal.CompanyInfoProjection;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PIReportDetails {
    CompanyInfoProjection billTo;
    CompanyInfoProjection shipTo;
    Map<PIReportHeaderDetails, List<ProFormaInvoiceItemValue>> glassItemDetails;
    List<ServiceRateInvoiceValue> serviceRateDetails;
    String totalQuantity;
    String totalUnitTotal;
    String totalRatePerUnit;
    String totalAmount;

}
