package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="confirm_through")
public class ConfirmThroughEntity {

    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "confirm_through_id", unique = true, nullable = false, updatable = false)
    private int confirmThroughId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String confirmThroughUuid;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="confirmThroughEntity",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceEntity> proFormaInvoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;


}
