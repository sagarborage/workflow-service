package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.ServiceRateValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "service_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ServiceRateEntity extends Base {
    private static final long serialVersionUID = -241370177952331642L;

    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private float rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    @OneToMany(mappedBy="serviceRateEntity",cascade=CascadeType.ALL)
    private List<ServiceRateInvoiceEntity> serviceRateInvoices;

    public ServiceRateValue toDTO() {
        return ServiceRateValue.newBuilder()
                .serviceRateId(getId())
                .serviceRateUuid(getUuid())
                .name(getName())
                .rate(getRate())
                //.tenantValue(getTenantEntity().toDTO())
                //.serviceRateInvoices(getServiceRateInvoices().stream().map(i->i.toDTO()).collect(Collectors.toList()))
                .createdDttm(getCreatedDatetime())
                .updatedDttm(getLastUpdatedDatetime())
                .createdBy(getCreatedBy())
                .updatedBy(getLastUpdatedBy())
                .isActive(isActive())
                .build();
    }
}
