package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "address_type")
@Getter
@Setter
public class AddressTypeEntity implements Serializable {

    private static final long serialVersionUID = 3981140897718611608L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_type_id", unique = true, nullable = false, updatable = false)
    private int addressTypeId;
    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String  addressTypeUuid;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    @OneToMany(mappedBy="addressTypeEntity",cascade=CascadeType.ALL)
    private List<CompanyAddressEntity> companyAddressEntity;

}
