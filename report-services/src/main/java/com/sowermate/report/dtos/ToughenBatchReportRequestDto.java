package com.sowermate.report.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ToughenBatchReportRequestDto {
    private String tenantUuid;
    private String companyUuid;
    private LocalDate batchItemDate;
}
