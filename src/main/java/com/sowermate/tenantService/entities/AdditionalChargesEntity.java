package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name="additional_charges")
public class AdditionalChargesEntity extends CommonEntity {

    private static final long serialVersionUID = -241370177952331642L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="additional_charges_id")
    private int additionalChargesId;

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
    private TenantDetailsEntity tenantDetailsEntities;

}
