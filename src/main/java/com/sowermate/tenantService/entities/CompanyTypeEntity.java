package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.CompanyTypeValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "company_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class CompanyTypeEntity extends Base {

    private static final long serialVersionUID = 3981140897718611608L;

/*    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyTypeId;*/

    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
/*    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    protected String  companyTypeUuid;*/
    
    @OneToMany(mappedBy="companyType",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompanyEntity> companyEntiies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public CompanyTypeValue toDTO() {
        return CompanyTypeValue.newBuilder()
                .companyTypeId(getId())
                .companyTypeUuid(getUuid())
                .type(getType())
                .description(getDescription())
                //.companyValue(Optional.ofNullable(getCompanyEntity()).map(CompanyEntity::toDTO).orElse(null))
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(isActive())
                .build();
    }
}
