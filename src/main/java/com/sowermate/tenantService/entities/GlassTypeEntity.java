package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="glass_type")
public class GlassTypeEntity  {
    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer glassTypeId;


    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String glassTypeUuid;

    @Column(name = "glass_name")
    private String glassName;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy="glassTypeEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;
}
