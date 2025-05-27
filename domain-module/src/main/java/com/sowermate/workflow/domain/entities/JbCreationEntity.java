package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.workflow.domain.entities.value.JbCreationValue;
import com.sowermate.workflow.domain.enums.ToughenBatchProcessStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "jb_creation")
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class JbCreationEntity extends Base {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toughen_batch_process_id", nullable = false)
    private ToughenBatchProcessEntity toughenBatchProcessEntity;

    @Column(name = "party_name")
    private String partyName;

    @Column(name = "width_mm")
    private Float widthMm;

    @Column(name = "height_mm")
    private Float heightMm;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glass_thickness_id")
    private GlassThicknessEntity glassThicknessEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ToughenBatchProcessStatusEnum status;

    public JbCreationValue toDTO() {
        return JbCreationValue.newBuilder()
                .uuid(getUuid())
                .toughenBatchProcessUuid(getToughenBatchProcessEntity().getUuid())
                .partyName(getPartyName())
                .widthMm(getWidthMm())
                .heightMm(getHeightMm())
                .quantity(getQuantity())
                .glassThicknessUuid(getGlassThicknessEntity().getUuid())
                .status(getStatus())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
