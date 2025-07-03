package com.sowermate.workflow.report.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatePassRequestDto {
    String gatePassUuid;
    String tenantUuid;
    String companyUuid;
}
