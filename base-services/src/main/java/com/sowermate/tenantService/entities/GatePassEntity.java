package com.sowermate.tenantService.entities;


import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.GatePassValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "gate_pass")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GatePassEntity extends Base {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity companyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_company_id", nullable = false)
    private CompanyEntity partyCompanyEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proforma_invoice_id", nullable = false)
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @Column(name = "gate_pass_no", nullable = false)
    private Integer gatePassNo;

    public GatePassValue toDTO() {
        return GatePassValue.newBuilder()
                .proFormaInvoiceUuid(getProFormaInvoiceEntity() == null ?null : getProFormaInvoiceEntity().getUuid())
                .tenantUuid(getTenantEntity()==null?null : getTenantEntity().getUuid())
                .companyUuid(getCompanyEntity()==null?null : getCompanyEntity().getUuid())
                .partyCompanyUuid(getPartyCompanyEntity()==null?null : getPartyCompanyEntity().getUuid())
                .gatePassNo(gatePassNo)
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .version(getVersion())
                .build();
    }
}

