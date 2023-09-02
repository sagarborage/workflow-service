package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.CommonEntity;
import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(callSuper = false)
@Table(name = "additional_charges")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class AdditionalChargesEntity implements Serializable {

    private static final long serialVersionUID = -241370177952331642L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer additionalChargesId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String additionalChargesUuid;

    @Column(name="created_dttm")
    private Date createdDttm;

    @Column(name="updated_dttm")
    private Date updatedDttm;

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

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;


    public AdditionalChargesValue toDTO() {
        return AdditionalChargesValue.newBuilder()
                .additionalChargesId(getAdditionalChargesId())
                .additionalChargesUuid(getAdditionalChargesUuid())
                .createdDttm(getCreatedDttm())
                .updatedDttm(getUpdatedDttm())
                .extraMm(getExtraMm())
                .insurance(getInsurance())
                .adminCharges(getAdminCharges())
                .forwardingCharges(getForwardingCharges())
                .gst(getGst())
                .createdBy(getCreatedBy())
                .updatedBy(getUpdatedBy())
                .tenantValue(getTenantEntity().toDTO())
                .build();
    }
}
