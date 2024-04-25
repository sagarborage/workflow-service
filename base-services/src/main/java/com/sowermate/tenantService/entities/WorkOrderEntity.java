package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.WorkOrderValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "work_order")
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class WorkOrderEntity extends Base {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proforma_invoice_id")
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firm_id")
    private CompanyEntity firm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenantEntity;

    @OneToOne(mappedBy = "workOrderEntity",cascade =CascadeType.ALL )
    private GlassBreakageDetailsEntity glassBreakageDetailsEntity;

    public WorkOrderValue toDTO() {
        return WorkOrderValue.newBuilder()
                .uuid(getUuid())
                .proFormaInvoiceUuid(getProFormaInvoiceEntity().getUuid())
                .tenantUuid(getTenantEntity().getUuid())
                .firmUuid(getFirm().getUuid())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
