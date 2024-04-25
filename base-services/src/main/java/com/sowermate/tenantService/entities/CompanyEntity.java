package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.CompanyValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class CompanyEntity extends Base {

    private static final long serialVersionUID = 1L;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenantEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_type_id")
    private CompanyTypeEntity companyType;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;


    public CompanyValue toDTO() {
        return CompanyValue.newBuilder()
                .companyId(getId())
                .companyUuid(getUuid())
                .companyName(getCompanyName())
                .cin(getCin())
                .gstin(getGstin())
                .tan(getTan())
                .pan(getPan())
                //.tenantUuid(getTenantEntity().getUuid())
                //.companyAddresses(getCompanyAddresses().stream().map(e->e.toDTO()).collect(Collectors.toList()))
                .companyTypeUuid(getCompanyType().getUuid())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
