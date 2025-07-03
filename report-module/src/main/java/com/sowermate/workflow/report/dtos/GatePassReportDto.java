package com.sowermate.workflow.report.dtos;

import com.sowermate.workflow.domain.entities.minimal.GlassInfoProjection;
import com.sowermate.workflow.domain.entities.value.GatePassValue;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class GatePassReportDto {
    List<String> gatePassTypes;
    String partyName;
    String address;
    String partyBillToName;
    List<GlassInfoProjection> glassInfoProjections;
    GatePassValue gatePassValue;
    String piNo;
    LocalDate gatePassDate;
    Integer totalQuantity;

}
