package com.sowermate.workflow.domain.entities.value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProFormaInvoiceExcelReport {
    private String tenantUuid;
    private String companyUuid;
    private String partyUuid;
    private String fromDate;
    private String toDate;
    private String status;
}
