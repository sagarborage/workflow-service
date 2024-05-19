package com.sowermate.tenantService.entities.value;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GatePassInfo {
    private String proFormInvoiceUuid;
    private Integer totalQuantity;
    private Integer dispatchedQuantity;
    private Integer gatePassBucket;
    private Boolean isIsGatePassCreationEnable;
    List<GatePassDetailsInfo> gatePassDetailsInfoList;
}
