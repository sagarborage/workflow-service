package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.BaseId;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serial;

//TODO: Will decide if want to use this entity or not later
@Getter
@Setter
@Entity
@Table(name="status")
public class StatusEntity extends BaseId {
    @Serial
    private static final long serialVersionUID = -241370177952331642L;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

}
