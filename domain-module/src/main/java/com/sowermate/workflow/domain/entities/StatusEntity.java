package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.workflow.domain.entities.value.StatusValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class StatusEntity extends Base {
    private static final long serialVersionUID = -241370177952331642L;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenantEntity;

    public StatusValue toDTO() {
        return StatusValue.newBuilder()
                .uuid(getUuid())
                .tenantUuid(tenantEntity.getUuid())
                .name(getName())
                .isActive(getIsActive())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .version(getVersion())
                .build();
    }
}
