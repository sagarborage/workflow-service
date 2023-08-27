package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.AddressTypeValue;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "address_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class AddressTypeEntity implements Serializable {

    private static final long serialVersionUID = 3981140897718611608L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressTypeId;

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
    private List<CompanyAddressEntity> companyAddresses;

    public AddressTypeValue toDTO() {
        return AddressTypeValue.newBuilder()
                .addressTypeId(getAddressTypeId())
                .addressTypeUuid(getAddressTypeUuid())
                .type(getType())
                .description(getDescription())
                .isActive(getIsActive())
                .tenantValue(getTenantEntity().toDTO())
                .companyAddresses(getCompanyAddresses().stream().map(c->c.toDTO()).collect(Collectors.toList()))
                .build();
    }
}
