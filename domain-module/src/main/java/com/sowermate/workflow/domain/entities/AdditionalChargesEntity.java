package com.sowermate.workflow.domain.entities;

import com.sowermate.core.base.entities.Base;
import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.workflow.domain.entities.value.AdditionalChargesValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "additional_charges")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class AdditionalChargesEntity extends Base {

    private static final long serialVersionUID = -241370177952331642L;

    @Column(name = "extra_mm")
    private float extraMm;

    @Column(name = "insurance")
    private float insurance;

    @Column(name = "admin_charges")
    private float adminCharges;

    @Column(name = "forwarding_charges")
    private float forwardingCharges;

    @Column(name = "gst")
    private float gst;

    @OneToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenantEntity;

    public AdditionalChargesValue toDTO() {
        return AdditionalChargesValue.newBuilder()
                .uuid(getUuid())
                .tenantUuid(tenantEntity.getUuid())
                .extraMm(getExtraMm())
                .insurance(getInsurance())
                .adminCharges(getAdminCharges())
                .forwardingCharges(getForwardingCharges())
                .gst(getGst())
                .isActive(getIsActive())
                .createdBy(getCreatedBy())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedBy(getLastUpdatedBy())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .version(getVersion())
                .build();
    }
}
