package com.sowermate.report.dtos;

import com.sowermate.tenantService.entities.minimal.GlassInfoProjection;
import com.sowermate.tenantService.entities.value.GatePassValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

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
