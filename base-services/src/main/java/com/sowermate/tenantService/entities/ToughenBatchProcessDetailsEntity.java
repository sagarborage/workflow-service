package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessDetailsValue;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "toughen_batch_process_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ToughenBatchProcessDetailsEntity extends Base {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proforma_invoice_id", nullable = false)
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrderEntity workOrderEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proforma_invoice_item_id", nullable = false)
    private ProFormaInvoiceItemEntity proFormaInvoiceItemEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ToughenBatchProcessStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toughen_batch_process_id")
    private ToughenBatchProcessEntity toughenBatchProcessEntity;

    public ToughenBatchProcessDetailsValue toDTO(){
        return ToughenBatchProcessDetailsValue.newBuilder()
                .uuid(getUuid())
                .proFormaInvoiceUuid(getProFormaInvoiceEntity().getUuid())
                .proFormaInvoiceItemUuid(getProFormaInvoiceItemEntity().getUuid())
                .workOrderUuid(getWorkOrderEntity().getUuid())
                .isActive(getIsActive())
                .build();
    }
}
