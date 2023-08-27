package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "service_rate_invoice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ServiceRateInvoiceEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceRateInvoiceId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String serviceRateInvoiceUuid;


    @Column(name = "quantity")
    private int quantity;

    @Column(name = "rate")
    private int rate;

    @Column(name = "total")
    private int total;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_forma_invoice_id")
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_rate_id")
    private ServiceRateEntity serviceRateEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public ServiceRateInvoiceValue toDTO() {
        return ServiceRateInvoiceValue.newBuilder()
                .serviceRateInvoiceId(getServiceRateInvoiceId())
                .serviceRateInvoiceUuid(getServiceRateInvoiceUuid())
                .quantity(getQuantity())
                .rate(getRate())
                .total(getTotal())
                .tenantValue(getTenantEntity().toDTO())
                .proFormaInvoice(getProFormaInvoiceEntity().toDTO())
                .serviceRate(getServiceRateEntity().toDTO())
                .build();
    }
}
