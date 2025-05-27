package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.workflow.domain.entities.value.ServiceRateValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    @JoinColumn(name = "tenant_id")
    private Tenant tenantEntity;

    @OneToMany(mappedBy = "serviceRateEntity", cascade = CascadeType.ALL)
    private List<ServiceRateInvoiceEntity> serviceRateInvoices;

    public ServiceRateValue toDTO() {
        return ServiceRateValue.newBuilder()
                .uuid(getUuid())
                .name(getName())
                .rate(getRate())
                .tenantUuid(tenantEntity.getUuid())
                //.tenantValue(getTenantEntity().toDTO())
                //.serviceRateInvoices(getServiceRateInvoices().stream().map(i->i.toDTO()).collect(Collectors.toList()))
                .isActive(getIsActive())
                .createdBy(getCreatedBy())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedBy(getLastUpdatedBy())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .version(getVersion())
                .build();
    }
}
