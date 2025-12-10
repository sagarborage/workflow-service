package com.sowermate.tenantService.entities.value;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GatePassExcelReportData {

    private Integer gatePassNo;
    private String createdBy;
    private String piNumber;
    private String partyName;
    private String firmName;
    private String vehicleDetails;
    private Integer quantity;
    private String dateTime;
}
