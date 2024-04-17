package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessValue;
import com.sowermate.tenantService.enums.ProformaInvoiceStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "pi_toughen_batch_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ToughenBatchProcessEntity extends Base {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantEntity tenantEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firm_id", nullable = false)
    private CompanyEntity companyEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proforma_invoice_id", nullable = false)
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrderEntity workOrderEntity;

    @JoinColumn(name = "proforma_invoice_item_id", nullable = false)
    private ProFormaInvoiceItemEntity proFormaInvoiceItemEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProformaInvoiceStatusEnum status;

    @Column(name = "status_details")
    private String statusDetails;

    public ToughenBatchProcessValue toDTO(){
        return ToughenBatchProcessValue.newBuilder()
                .uuid(getUuid())
                .tenantUuid(getTenantEntity().getUuid())
                .proFormaInvoiceUuid(getProFormaInvoiceEntity().getUuid())
                .proFormaInvoiceItemUuid(getProFormaInvoiceItemEntity().getUuid())
                .workOrderUuid(getWorkOrderEntity().getUuid())
                .isActive(getIsActive())
                .build();
    }
}
