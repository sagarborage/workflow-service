package com.sowermate.report.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;

@Getter
@Setter
public class GatePassRequestDto {
    String gatePassUuid;
    String tenantUuid;
    String companyUuid;
}
