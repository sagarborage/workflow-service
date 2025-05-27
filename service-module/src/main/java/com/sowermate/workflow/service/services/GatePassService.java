package com.sowermate.workflow.service.services;

import com.sowermate.workflow.domain.entities.minimal.GlassInfoProjection;
import com.sowermate.workflow.domain.entities.value.GatePassInfo;
import com.sowermate.workflow.domain.entities.value.GatePassValue;
import com.sowermate.workflow.domain.projection.PiInfoProjectionForReport;

import java.util.List;

public interface GatePassService {

    public GatePassValue createGatePass(GatePassValue gatePassValue);

    public GatePassValue updateGatePass(GatePassValue gatePassValue);

    public List<GatePassValue> getAllGatePass(String tenantUuid, String companyUuid);

    public GatePassValue getGatePass(String uuid, String tenantUuid, String companyUuid);

    public List<GlassInfoProjection> getGlassInfoForReport(String uuid);

    public PiInfoProjectionForReport getPiInfoForReport(String uuid);

    public GatePassValue deleteGatePass(String uuid, String tenantUuid, String companyUuid);

    public GatePassInfo getGatePassByProformaInvoice(String companyUuid, String proformaInvoiceUuid);
}
