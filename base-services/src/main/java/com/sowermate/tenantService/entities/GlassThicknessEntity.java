package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.GlassThicknessValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "glass_thickness")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GlassThicknessEntity extends Base {
    private static final long serialVersionUID = -241370177952331642L;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="glassThicknessEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    @OneToOne(mappedBy="glassThicknessEntity",fetch = FetchType.LAZY)
    private JbCreationEntity jbCreationEntity;

    public GlassThicknessValue toDTO() {
        return GlassThicknessValue.newBuilder()
                .glassThicknessId(getId())
                .glassThicknessUuid(getUuid())
                .name(getName())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}

