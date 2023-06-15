package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="glass_specification")
public class GlassSpecificationEntity {

    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "glass_specification_id", unique = true, nullable = false, updatable = false)
    private int glassSpecificationId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String uuid;
    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy="glassSpecificationEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntity;

}
