package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.workflow.domain.entities.value.ToughenBatchProcessDetailsValue;
import com.sowermate.workflow.domain.enums.ToughenBatchProcessStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proforma_invoice_item_id", nullable = false)
    private ProFormaInvoiceItemEntity proFormaInvoiceItemEntity;

    @Column
    private Integer stickerNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ToughenBatchProcessStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toughen_batch_process_id")
    private ToughenBatchProcessEntity toughenBatchProcessEntity;

    public ToughenBatchProcessDetailsValue toDTO() {
        return ToughenBatchProcessDetailsValue.newBuilder()
                .uuid(getUuid())
                .proFormaInvoiceUuid(getProFormaInvoiceEntity().getUuid())
                .proFormaInvoiceItemUuid(getProFormaInvoiceItemEntity().getUuid())
                .stickerNumber(getStickerNumber())
                .workOrderUuid(getWorkOrderEntity().getUuid())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .version(getVersion())
                .build();
    }
}
