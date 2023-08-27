package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "glass_specification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GlassSpecificationEntity implements Serializable {

    private static final long serialVersionUID = -241370177952331642L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer glassSpecificationId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String glassSpecificationUuid;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy="glassSpecificationEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public GlassSpecificationValue toDTO() {
        return GlassSpecificationValue.newBuilder()
                .glassSpecificationId(getGlassSpecificationId())
                .glassSpecificationUuid(getGlassSpecificationUuid())
                .tenantValue(getTenantEntity().toDTO())
                .name(getName())
                .isActive(getIsActive())
                .build();
    }
}
