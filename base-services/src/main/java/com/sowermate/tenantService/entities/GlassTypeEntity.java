package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.GlassTypeValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "glass_type")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GlassTypeEntity extends Base {

    @Column(name = "glass_name")
    private String glassName;

    @OneToMany(mappedBy="glassTypeEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceItemEntity> proFormaInvoiceItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public GlassTypeValue toDTO() {
        return GlassTypeValue.newBuilder()
                .glassTypeId(getId())
                .glassTypeUuid(getUuid())
                .glassName(getGlassName())
                .tenantUuid(getTenantEntity().getUuid())
                //.proFormaInvoiceItems(Optional.ofNullable(getProFormaInvoiceItems())
                  //      .map(e -> e.stream().map(ProFormaInvoiceItemEntity::toDTO).collect(Collectors.toList())).orElse(Collections.emptyList()))
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
