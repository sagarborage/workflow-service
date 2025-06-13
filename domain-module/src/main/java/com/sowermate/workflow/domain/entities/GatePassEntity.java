package com.sowermate.workflow.domain.entities;


import com.sowermate.core.base.entities.Base;
import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.workflow.domain.entities.value.GatePassValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "gate_pass")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GatePassEntity extends Base {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Tenant companyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_company_id", nullable = false)
    private Tenant partyCompanyEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proforma_invoice_id", nullable = false)
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @Column(name = "gate_pass_no", nullable = false)
    private Integer gatePassNo;

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "vehicle_no")
    private String vehicleNo;

    @Column(name = "driver_contact_no")
    private String driverContactNo;

    @OneToMany(mappedBy = "gatePassEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GatePassDetailsEntity> gatePassDetailsEntities;

    public GatePassValue toDTO() {
        return GatePassValue.newBuilder()
                .uuid(getUuid())
                .proFormaInvoiceUuid(getProFormaInvoiceEntity() == null ? null : getProFormaInvoiceEntity().getUuid())
                .tenantUuid(getTenantEntity() == null ? null : getTenantEntity().getUuid())
                .companyUuid(getCompanyEntity() == null ? null : getCompanyEntity().getUuid())
                .partyCompanyUuid(getPartyCompanyEntity() == null ? null : getPartyCompanyEntity().getUuid())
                .gatePassNo(getGatePassNo())
                .driverName(getDriverName())
                .driverContactNo(getDriverContactNo())
                .vehicleNo(getVehicleNo())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .version(getVersion())
                .build();
    }
}

