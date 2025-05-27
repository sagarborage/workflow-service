package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.workflow.domain.entities.value.ServiceRateInvoiceValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "service_rate_invoice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ServiceRateInvoiceEntity extends Base {

    private static final long serialVersionUID = 1L;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "total")
    private Double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_forma_invoice_id")
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_rate_id")
    private ServiceRateEntity serviceRateEntity;

    public ServiceRateInvoiceValue toDTO() {
        return ServiceRateInvoiceValue.newBuilder()
                .uuid(getUuid())
                .proFormaInvoiceUuid(proFormaInvoiceEntity.getUuid())
                .serviceRateUuid(getServiceRateEntity().getUuid())
                .serviceRateName(getServiceRateEntity().getName())
                .quantity(getQuantity())
                .rate(getRate())
                .total(getTotal())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .version(getVersion())
                .build();
    }
}
