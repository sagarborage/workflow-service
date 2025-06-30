package com.sowermate.tenantService.entities.value;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WorkOrderExcelReportData {

    private String firmName;
    private String piNumber;
    private String piType;
    private String partyName;
    private String piDate;
    private String workOrderDate;
    private String amount;
    private String user;
    private Double SQFT;
    private Double SQMTR;
}
