package com.sowermate.workflow.report.dtos;

import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceItemReportValue;
import com.sowermate.workflow.domain.entities.value.ServiceRateInvoiceValue;
import com.sowermate.workflow.report.controllers.PIReportHeaderDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PIReportDetails {
    String billToAddress;
    String firmName;
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
