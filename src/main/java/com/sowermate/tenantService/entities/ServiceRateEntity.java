package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.ServiceRateValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString(callSuper = false)
@Table(name = "service_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ServiceRateEntity {
    private static final long serialVersionUID = -241370177952331642L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceRateId;

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
    private List<ServiceRateInvoiceEntity> serviceRateInvoices;

    public ServiceRateValue toDTO() {
        return ServiceRateValue.newBuilder()
                .serviceRateId(getServiceRateId())
                .serviceRateUuid(getServiceRateUuid())
                .name(getName())
                .rate(getRate())
                .isActive(getIsActive())
                .tenantValue(getTenantEntity().toDTO())
                .serviceRateInvoices(getServiceRateInvoices().stream().map(i->i.toDTO()).collect(Collectors.toList()))
                .build();
    }


}
