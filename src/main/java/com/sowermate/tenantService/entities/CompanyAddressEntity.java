package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@Entity
@Table(name="company_address")
public class CompanyAddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_address_id", unique = true, nullable = false, updatable = false)
    private int companyAddressId;

    @Column(name="uuid", unique=true, updatable=false)
    private String companyAddressUuid;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="address_type_id")
    private AddressTypeEntity addressTypeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="company_id")
    private CompanyEntity companyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="address_id")
    private AddressEntity addressEntity;

}
