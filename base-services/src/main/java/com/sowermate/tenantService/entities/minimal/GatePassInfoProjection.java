package com.sowermate.tenantService.entities.minimal;

import com.sowermate.tenantService.entities.value.GatePassDetailsInfo;

import java.util.List;

public interface GatePassInfoProjection {
     Integer getTotalQuantity();
     Integer getDispatchedQuantity();
     Integer getGatePassBucket();
}
