package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "service_rate_invoice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ServiceRateInvoiceEntity extends Base {

    private static final long serialVersionUID = 1L;

    @Column(name = "quantity")
    private int quantity;

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
                .id(getId())
                .uuid(getUuid())
                .serviceRateUuid(getServiceRateEntity().getUuid())
                .serviceRateName(getServiceRateEntity().getName())
                .quantity(getQuantity())
                .rate(getRate())
                .total(getTotal())
                .isActive(getIsActive())
                .build();
    }
}
