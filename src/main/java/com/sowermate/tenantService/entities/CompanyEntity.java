package com.sowermate.tenantService.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Table(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class CompanyEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    @Column(name = "uuid", unique = true, updatable = false)
    protected String companyUuid;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "CIN")
    private String cin;

    @Column(name = "GSTIN")
    private String gstin;

    @Column(name = "TAN")
    private String tan;

    @Column(name = "PAN")
    private String pan;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_type_id")
    private CompanyTypeEntity companyTypeEntity;

    @OneToMany(mappedBy = "companyIdBill", cascade = CascadeType.ALL)
    private List<ProFormaInvoiceEntity> proFormaInvoiceEntities;

    @OneToMany(mappedBy = "companyEntity", cascade = CascadeType.ALL)
    private List<CompanyAddressEntity> companyAddressEntities;

}
