package com.sowermate.report.dtos;

import com.sowermate.tenantService.entities.minimal.CompletedGlassesProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ToughenBatchReportDto {
    String partyName;
    LocalDate date;
    List<CompletedGlassValue> completedWorkOrders;
    List<CompletedGlassValue> completedJb;
}
