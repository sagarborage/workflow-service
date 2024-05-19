package com.sowermate.report.dtos;

import lombok.Getter;
import org.springframework.web.bind.annotation.PathVariable;

@Getter
public class GatePassRequestDto {
    String gatePassUuid;
    String tenantUuid;
    String companyUuid;
}
