package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.CommonEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name="company")
public class CompanyEntity {
    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id", unique = true, nullable = false, updatable = false)
    private int companyId;

    @Column(name="uuid", unique=true, updatable=false)
    private String companyUuid;

    @Column(name="created_dttm")
    private Date createdDttm;

    @Column(name="updated_dttm")
    private Date updatedDttm;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "CIN")
    private int cin;

    @Column(name = "GSTIN")
    private int gstin;

    @Column(name = "TAN")
    private int tan;

    @Column(name = "PAN")
    private String pan;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;


    @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name ="tenant_id")
   private TenantEntity tenantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_type_id")
    private CompanyTypeEntity companyTypeEntity;

    @OneToMany(mappedBy="companyIdBill",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceEntity> proFormaInvoiceEntity;

    @OneToMany(mappedBy="companyIdShip",cascade=CascadeType.ALL)
    private List<ProFormaInvoiceEntity> proFormaInvoiceEntity1;

}
