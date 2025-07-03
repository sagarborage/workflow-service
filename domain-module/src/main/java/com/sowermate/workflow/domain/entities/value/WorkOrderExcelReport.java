package com.sowermate.workflow.domain.entities.value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class WorkOrderExcelReport {
    private String tenantUuid;
    private String workOrderUuid;
    private String fromDate;
    private String toDate;
}
