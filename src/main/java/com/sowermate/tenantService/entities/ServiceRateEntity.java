package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "service_rate")
public class ServiceRateEntity {
    private static final long serialVersionUID = -241370177952331642L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="service_rate_id",unique = true, nullable = false, updatable = false)
    private int serviceRateId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String serviceRateUuid;

    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private float rate;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    @OneToMany(mappedBy="serviceRateEntity",cascade=CascadeType.ALL)
    private List<ServiceRateInvoiceEntity> serviceRateInvoiceEntity;

}
