package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.CompanyTypeValue;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "company_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class CompanyTypeEntity  implements Serializable {

    private static final long serialVersionUID = 3981140897718611608L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyTypeId;

    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    protected String  companyTypeUuid;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;
    
    @OneToOne(mappedBy="companyType",cascade=CascadeType.ALL)
    private CompanyEntity companyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public CompanyTypeValue toDTO() {
        return CompanyTypeValue.newBuilder()
                .companyTypeId(getCompanyTypeId())
                .type(getType())
                .description(getDescription())
                .companyTypeUuid(getCompanyTypeUuid())
                .isActive(getIsActive())
                .companyValue(Optional.ofNullable(getCompanyEntity()).map(CompanyEntity::toDTO).orElse(null))
                .build();
    }
}
