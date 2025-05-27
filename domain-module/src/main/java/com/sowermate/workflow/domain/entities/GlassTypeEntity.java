package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.workflow.domain.entities.value.GlassTypeValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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

    @OneToMany(mappedBy = "glassTypeEntity", cascade = CascadeType.ALL)
    private List<ProFormaInvoiceItemEntity> proFormaInvoiceItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenantEntity;

    public GlassTypeValue toDTO() {
        return GlassTypeValue.newBuilder()
                .uuid(getUuid())
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
