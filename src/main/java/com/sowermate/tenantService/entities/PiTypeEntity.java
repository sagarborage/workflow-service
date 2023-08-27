package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.PiTypeValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "pi_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class PiTypeEntity {
    private static final long serialVersionUID = -241370177952331642L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer piTypeId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String piTypeUuid;

    @Column(name = "pi_type_name")
    private String piTypeName;

    @OneToMany(mappedBy="piTypeEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceEntity> proFormaInvoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public PiTypeValue toDTO() {
        return PiTypeValue.newBuilder()
                .piTypeId(getPiTypeId())
                .piTypeUuid(getPiTypeUuid())
                .piTypeName(getPiTypeName())
                .tenantValue(getTenantEntity().toDTO())
                .build();
    }

}
