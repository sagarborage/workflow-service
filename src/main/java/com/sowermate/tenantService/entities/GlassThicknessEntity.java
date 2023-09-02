package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.GlassThicknessValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = false)
@Table(name = "glass_thickness")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GlassThicknessEntity {
    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer glassThicknessId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String glassThicknessUuid;
    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy="glassThicknessEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public GlassThicknessValue toDTO() {
        return GlassThicknessValue.newBuilder()
                .glassThicknessId(getGlassThicknessId())
                .glassThicknessUuid(getGlassThicknessUuid())
                .name(getName())
                .isActive(getIsActive())
                .build();
    }
}

