package com.sowermate.workflow.domain.entities.value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProFormaInvoiceExcelReportData {
    private String piNumber;
    private String partyName;
    private String invoiceDateTime;
    private String amount;
    private String user;
    private String status;
}
