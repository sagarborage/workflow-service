package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessValue;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import jakarta.persistence.*;
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

    @Column(name="batch_no")
    private Integer batchNo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantEntity tenantEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firm_id", nullable = false)
    private CompanyEntity companyEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ToughenBatchProcessStatusEnum status;

    @OneToMany(mappedBy="toughenBatchProcessEntity",cascade=CascadeType.ALL, orphanRemoval = true)
    private List<ToughenBatchProcessDetailsEntity> toughenBatchProcessDetailsEntities;

    @PostPersist
    void setToughenBatchProcessValue(){
        for(ToughenBatchProcessDetailsEntity entity : toughenBatchProcessDetailsEntities) {
            entity.setToughenBatchProcessEntity(this);
        }
    }

    public ToughenBatchProcessValue toDTO(){
        return ToughenBatchProcessValue.newBuilder()
                .uuid(getUuid())
                .tenantUuid(getTenantEntity().getUuid())
                .companyUuid(getCompanyEntity().getUuid())
                .status(getStatus())
                .toughenBatchProcessDetailsValues(getToughenBatchProcessDetailsEntities().stream()
                        .map(ToughenBatchProcessDetailsEntity::toDTO).collect(Collectors.toList()))
                .isActive(getIsActive())
                .build();
    }
}
