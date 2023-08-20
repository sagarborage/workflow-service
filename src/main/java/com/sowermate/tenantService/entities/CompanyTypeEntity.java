package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "company_type")
@Getter
@Setter
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
    
    @OneToMany(mappedBy="companyTypeEntity",cascade=CascadeType.ALL)
    private List<CompanyEntity> companyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

}
