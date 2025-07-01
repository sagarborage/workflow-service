package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.minimal.GlassInfoProjection;
import com.sowermate.tenantService.entities.value.GatePassExcelReport;
import com.sowermate.tenantService.entities.value.GatePassExcelReportData;
import com.sowermate.tenantService.entities.value.GatePassInfo;
import com.sowermate.tenantService.entities.value.GatePassValue;
import com.sowermate.tenantService.entities.value.WorkOrderExcelReport;
import com.sowermate.tenantService.entities.value.WorkOrderExcelReportData;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GatePassService {

    public GatePassValue createGatePass(GatePassValue gatePassValue);

    public GatePassValue updateGatePass(GatePassValue gatePassValue);

    public List<GatePassValue> getAllGatePass(String tenantUuid,String companyUuid);

    List<Map<String, Object>> getAllGatePassWithProformaDetails(String tenantUuid, String companyUuid, String firmUuid, LocalDate fromDate, LocalDate toDate);

    public GatePassValue getGatePass(String uuid, String tenantUuid,String companyUuid);
    public List<GlassInfoProjection> getGlassInfoForReport(String uuid);
    public PiInfoProjectionForReport getPiInfoForReport(String uuid);

    public GatePassValue deleteGatePass(String uuid, String tenantUuid, String companyUuid);

    public GatePassInfo getGatePassByProformaInvoice(String companyUuid, String proformaInvoiceUuid);

    List<GatePassExcelReportData> getGatePassExcel(GatePassExcelReport gatePassExcelReport);
}
