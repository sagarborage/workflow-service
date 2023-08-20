package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "service_rate_invoice")
@Getter
@Setter
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
}
