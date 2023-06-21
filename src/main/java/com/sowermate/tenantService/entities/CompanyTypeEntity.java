package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company_type")
@Getter
@Setter
public class CompanyTypeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_type_id", unique = true, nullable = false, updatable = false)
    private Integer companyTypeId;
    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String  companyTypeUuid;;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;


    @OneToMany(mappedBy="companyTypeEntity",cascade=CascadeType.ALL)
    private List<CompanyEntity> companyEntity;

}
