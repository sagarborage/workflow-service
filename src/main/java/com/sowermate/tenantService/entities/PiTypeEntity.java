package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.PiTypeValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pi_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class PiTypeEntity extends Base {
    private static final long serialVersionUID = -241370177952331642L;

    @Column(name = "pi_type_name")
    private String piTypeName;

    @OneToMany(mappedBy="piTypeEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceEntity> proFormaInvoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public PiTypeValue toDTO() {
        return PiTypeValue.newBuilder()
                .piTypeId(getId())
                .piTypeUuid(getUuid())
                .piTypeName(getPiTypeName())
                .isActive(isActive())
                .createdDttm(getCreatedDatetime())
                .updatedDttm(getLastUpdatedDatetime())
                .createdBy(getCreatedBy())
                .updatedBy(getLastUpdatedBy())
                .build();
    }

}
