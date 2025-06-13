package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.workflow.domain.entities.value.PiTypeValue;
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
@Table(name = "pi_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class PiTypeEntity extends Base {
    private static final long serialVersionUID = -241370177952331642L;

    @Column(name = "pi_type_name")
    private String piTypeName;

    @OneToMany(mappedBy = "piTypeEntity", cascade = CascadeType.ALL)
    private List<ProFormaInvoiceEntity> proFormaInvoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenantEntity;

    public PiTypeValue toDTO() {
        return PiTypeValue.newBuilder()
                .uuid(getUuid())
                .tenantUuid(tenantEntity.getUuid())
                .piTypeName(getPiTypeName())
                .isActive(getIsActive())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .build();
    }
}
