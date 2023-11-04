package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public AdditionalChargesValue toDTO() {
        return AdditionalChargesValue.newBuilder()
                .additionalChargesId(getId())
                .additionalChargesUuid(getUuid())
                .extraMm(getExtraMm())
                .insurance(getInsurance())
                .adminCharges(getAdminCharges())
                .forwardingCharges(getForwardingCharges())
                .gst(getGst())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
