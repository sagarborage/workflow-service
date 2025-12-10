package com.sowermate.tenantService.entities.value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatePassDetailsInfo {
    private String partyName;
    private String proFormInvoiceUuid;
    private String gatePassUuid;
    private Integer gatePassNo;
    private Integer quantity;
}
