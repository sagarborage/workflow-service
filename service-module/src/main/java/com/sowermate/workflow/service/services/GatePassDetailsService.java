package com.sowermate.workflow.service.services;

import com.sowermate.workflow.domain.entities.value.GatePassDetailsValue;

import java.util.List;

public interface GatePassDetailsService {

    GatePassDetailsValue createGatePassDetails(GatePassDetailsValue gatePassDetailsValue);

    GatePassDetailsValue updateGatePassDetails(GatePassDetailsValue gatePassDetailsValue);

    GatePassDetailsValue getGatePassDetails(String gatePassDetailsUuid, String proFormaInvoiceItemUuid, String gatePassUuid);

    List<GatePassDetailsValue> getAllGatePassDetails(String proFormaInvoiceItemUuid, String gatePassUuid);

    GatePassDetailsValue deleteGatePassDetails(String gatePassDetailsUuid, String proFormaInvoiceItemUuid, String gatePassUuid);
}
