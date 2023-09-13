package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.GlassTypeValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "glass_type")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GlassTypeEntity extends Base {
    //private static final long serialVersionUID = -241370177952331642L;

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
                .createdDttm(getCreatedDatetime())
                .updatedDttm(getLastUpdatedDatetime())
                .createdBy(getCreatedBy())
                .updatedBy(getLastUpdatedBy())
                .isActive(isActive())
                .build();
    }
}
