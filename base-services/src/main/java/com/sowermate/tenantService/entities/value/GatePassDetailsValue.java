package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
import com.sowermate.tenantService.entities.AddressEntity;
import com.sowermate.tenantService.entities.GatePassDetailsEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GatePassDetailsValue extends BaseDto {
    private Integer gatePassQty;
    private String proFormaInvoiceItemUuid;
    private String gatePassUuid;

    public GatePassDetailsEntity toEntity() {
        return GatePassDetailsEntity.newBuilder()
                .uuid(getUuid())
                .isActive(getIsActive())
                .gatePassQty(getGatePassQty())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .build();
    }
}
