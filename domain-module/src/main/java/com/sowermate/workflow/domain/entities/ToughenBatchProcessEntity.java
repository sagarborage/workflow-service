package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.workflow.domain.entities.value.ToughenBatchProcessValue;
import com.sowermate.workflow.domain.enums.ToughenBatchProcessStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "toughen_batch_process")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ToughenBatchProcessEntity extends Base {

    @Column(name = "batch_no")
    private Integer batchNo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenantEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firm_id", nullable = false)
    private Tenant companyEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ToughenBatchProcessStatusEnum status;

    @OneToMany(mappedBy = "toughenBatchProcessEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToughenBatchProcessDetailsEntity> toughenBatchProcessDetailsEntities;

    @OneToMany(mappedBy = "toughenBatchProcessEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JbCreationEntity> jbCreationEntities;

    @PostPersist
    void executePostPersist() {
        if (null != toughenBatchProcessDetailsEntities) {
            for (ToughenBatchProcessDetailsEntity entity : toughenBatchProcessDetailsEntities) {
                entity.setToughenBatchProcessEntity(this);
            }
        }
        if (null != jbCreationEntities) {
            for (JbCreationEntity entity : jbCreationEntities) {
                entity.setToughenBatchProcessEntity(this);
            }
        }
    }

    public ToughenBatchProcessValue toDTO() {
        return ToughenBatchProcessValue.newBuilder()
                .uuid(getUuid())
                .tenantUuid(getTenantEntity().getUuid())
                .companyUuid(getCompanyEntity().getUuid())
                .status(getStatus())
                .toughenBatchProcessDetailsValues(getToughenBatchProcessDetailsEntities().stream()
                        .map(ToughenBatchProcessDetailsEntity::toDTO).collect(Collectors.toList()))
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .version(getVersion())
                .build();
    }
}
