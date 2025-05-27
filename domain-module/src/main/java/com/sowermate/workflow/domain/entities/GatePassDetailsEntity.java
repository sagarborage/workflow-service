package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.workflow.domain.entities.value.GatePassDetailsValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "gate_pass_details")
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GatePassDetailsEntity extends Base {

    private static final long serialVersionUID = -241370177952331642L;

    @Column(name = "gate_pass_qty")
    private Integer gatePassQty;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Proforma_invoice_item_id")
    private ProFormaInvoiceItemEntity proFormaInvoiceItemEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gate_pass_id")
    private GatePassEntity gatePassEntity;

    public GatePassDetailsValue toDTO() {
        return GatePassDetailsValue.newBuilder()
                .uuid(getUuid())
                .gatePassQty(getGatePassQty())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .version(getVersion())
                .proFormaInvoiceItemUuid(proFormaInvoiceItemEntity.getUuid())
                .gatePassUuid(gatePassEntity.getUuid())
                .build();
    }

}
