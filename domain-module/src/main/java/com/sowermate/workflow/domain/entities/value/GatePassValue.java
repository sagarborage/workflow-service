package com.sowermate.workflow.domain.entities.value;

import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.entities.GatePassEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GatePassValue extends BaseDto {

    private String tenantUuid;

    private String companyUuid;

    private String partyCompanyUuid;

    private String proFormaInvoiceUuid;

    private Integer gatePassNo;

    private String driverName;

    private String vehicleNo;

    private String driverContactNo;


    public GatePassEntity toEntity() {
        return GatePassEntity.newBuilder()
                .uuid(getUuid())
                .gatePassNo(getGatePassNo())
                .driverName(getDriverName())
                .driverContactNo(getDriverContactNo())
                .vehicleNo(getVehicleNo())
                .createdBy(getCreatedBy())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedBy(getLastUpdatedBy())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .version(getVersion())
                .isActive(getIsActive())
                .build();
    }

}
