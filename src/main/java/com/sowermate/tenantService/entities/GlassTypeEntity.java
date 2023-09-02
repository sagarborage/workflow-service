package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.GlassTypeValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString(callSuper = false)
@Table(name = "glass_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GlassTypeEntity  {
    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer glassTypeId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String glassTypeUuid;

    @Column(name = "glass_name")
    private String glassName;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy="glassTypeEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceItemEntity> proFormaInvoiceItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public GlassTypeValue toDTO() {
        return GlassTypeValue.newBuilder()
                .glassTypeId(getGlassTypeId())
                .glassTypeUuid(getGlassTypeUuid())
                .glassName(getGlassName())
                .isActive(getIsActive())
                .tenantValue(getTenantEntity().toDTO())
                .proFormaInvoiceItems(getProFormaInvoiceItems().stream().map(e->e.toDTO()).collect(Collectors.toList()))
                .build();
    }
}
