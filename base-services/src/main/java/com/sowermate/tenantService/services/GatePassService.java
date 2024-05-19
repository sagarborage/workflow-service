package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.GatePassInfo;
import com.sowermate.tenantService.entities.value.GatePassValue;
import com.sowermate.tenantService.entities.value.GatePassValue;

import java.util.List;

public interface GatePassService {

    public GatePassValue createGatePass(GatePassValue gatePassValue);

    public GatePassValue updateGatePass(GatePassValue gatePassValue);

    public List<GatePassValue> getAllGatePass(String tenantUuid,String companyUuid);

    public GatePassValue getGatePass(String uuid, String tenantUuid);

    public GatePassValue deleteGatePass(String uuid, String tenantUuid);

    public GatePassInfo getGatePassByProformaInvoice(String companyUuid, String proformaInvoiceUuid);
}
