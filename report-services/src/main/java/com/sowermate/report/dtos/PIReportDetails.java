package com.sowermate.report.dtos;

import com.sowermate.report.controllers.PIReportHeaderDetails;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemReportValue;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PIReportDetails {
    String billToAddress;
    String billToPartyName;
    String shipToAddress;
    String shipToPartyName;
    String billToPartyStateCode;
    String shippingAddress;
    Map<PIReportHeaderDetails, List<ProFormaInvoiceItemReportValue>> glassItemDetails;
    List<ServiceRateInvoiceValue> serviceRateDetails;
    String totalQuantity;
    String totalUnitTotal;
    String sumSqFtTotal;
    String totalRatePerUnit;
    String totalAmount;
    String unitLabel;
    String gstType;
    String iGst;
    String cGst;
    String sGst;
    String iPercent;
    String iPercentAmount;
    String proxSqft;
    String proxSqftRate;
    String proxAmount;
    Double grandTotal;
    String formattedInvoiceDate;
    String formattedWorkOrderDate;
}
