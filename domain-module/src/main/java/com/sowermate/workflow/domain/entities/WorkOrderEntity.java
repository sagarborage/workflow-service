package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.workflow.domain.entities.value.WorkOrderValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
    private Tenant firm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenantEntity;


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
                .version(getVersion())
                .build();
    }
}
