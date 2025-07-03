package com.sowermate.workflow.domain.entities.value;

import lombok.Getter;
import lombok.Setter;

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
