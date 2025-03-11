package com.sowermate.tenantService.entities.value;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ToughReportDto {
    private LocalDate date;
    private Double workOrder;
    private Double jb;
    private Double reject;
    private Double totalCompleted;
    private List<String> thickness = new ArrayList<>();
    private Map<String, Double> workOrderSqft = new HashMap<>();
    private Map<String, Double> jbSqft = new HashMap<>();
    private Map<String, Double> rejectSqft = new HashMap<>();
    private Map<String, Double> totalSqft = new HashMap<>();

    public ToughReportDto() {
        this.thickness = new ArrayList<>();
        this.workOrderSqft = new HashMap<>();
        this.jbSqft = new HashMap<>();
        this.rejectSqft = new HashMap<>();
        this.totalSqft = new HashMap<>();
    }
}
