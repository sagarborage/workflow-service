package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.CompanyAddressValue;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "company_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class CompanyAddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyAddressId;

    @Column(name="uuid", unique=true, updatable=false)
    private String companyAddressUuid;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="company_id")
    private CompanyEntity companyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="address_id")
    private AddressEntity addressEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="address_type_id")
    private AddressTypeEntity addressTypeEntity;

    public CompanyAddressValue toDTO() {
        return CompanyAddressValue.newBuilder()
                .companyAddressId(getCompanyAddressId())
                .companyAddressUuid(getCompanyAddressUuid())
                .isActive(getIsActive())
                .companyValue(getCompanyEntity().toDTO())
                .addressValue(getAddressEntity().toDTO())
                .addressTypeValue(getAddressTypeEntity().toDTO())
                .build();
    }

}
