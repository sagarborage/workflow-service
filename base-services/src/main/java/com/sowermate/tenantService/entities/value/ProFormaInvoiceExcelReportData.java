package com.sowermate.tenantService.entities.value;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
