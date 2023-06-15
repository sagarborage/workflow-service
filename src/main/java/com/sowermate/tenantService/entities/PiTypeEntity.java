package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="pi_type")
public class PiTypeEntity {
    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pi_type_id", unique = true, nullable = false, updatable = false)
    private int piTypeId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String uuid;

    @Column(name = "mm")
    private float mm;

    @Column(name = "sqft")
    private float sqft;

    @OneToMany(mappedBy="piTypeEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceEntity> proFormaInvoiceEntity;


}
