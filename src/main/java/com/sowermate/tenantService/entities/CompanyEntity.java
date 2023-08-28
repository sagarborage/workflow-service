package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.CompanyValue;
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
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_type_id")
    private CompanyTypeEntity companyType;

    @OneToMany(mappedBy = "companyEntity")
    //@JoinColumn(name="company_id")
    List<CompanyAddressEntity> companyAddresses;

    public CompanyValue toDTO() {
        return CompanyValue.newBuilder()
                .companyId(getCompanyId())
                .companyUuid(getCompanyUuid())
                .companyName(getCompanyName())
                .cin(getCin())
                .gstin(getGstin())
                .tan(getTan())
                .pan(getPan())
                .isActive(getIsActive())
                .tenantValue(getTenantEntity().toDTO())
                .companyAddresses(getCompanyAddresses().stream().map(e->e.toDTO()).collect(Collectors.toList()))
                .companyType(getCompanyType().toDTO())
                .build();
    }
}
