package com.sowermate.tenantService.entities.value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatePassExcelReport {

    private String tenantUuid;
    private String partyUuid;
    private String firmUuid;
    private String fromDate;
    private String toDate;
}
