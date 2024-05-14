package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.base.dtos.BaseDto;
import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.GatePassEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.TenantEntity;
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


    public GatePassEntity toEntity() {
        return GatePassEntity.newBuilder()
                .uuid(getUuid())
                .gatePassNo(getGatePassNo())
                .version(getVersion())
                .isActive(getIsActive())
                .build();
    }

}
