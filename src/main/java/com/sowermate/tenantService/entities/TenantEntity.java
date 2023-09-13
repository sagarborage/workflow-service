package com.sowermate.tenantService.entities;


import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.TenantValue;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@Table(name = "tenant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class TenantEntity extends Base {

    private static final long serialVersionUID = -241370177952331642L;

    @Column(name="tenant_name")
    private  String tenantName;
    @Column(name="address")
    private String  address;
    @Column(name="city")
    private  String city;
    @Column(name="state")
    private  String state;
    @Column(name="country_id")
    private  int countryId;
    @Column(name="pin_code")
    private  String pinCode;
    @Column(name="phone_number")
    private  String phoneNumber;
    @Column(name="email_id")
    private  String emailId;
    @Column(name="activation_date")
    private Date activationDate;
    @Column(name="expiry_date")
    private  Date expiryDate;
    @Column(name="grace_period")
    private  int gracePeriod;
/*    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;*/

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompanyEntity> companyEntities;//OK

    @OneToOne(mappedBy="tenantEntity")
    private AdditionalChargesEntity additionalChargesEntity;//OK

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceRateEntity> serviceRateEntities;//OK

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<GlassTypeEntity> glassTypeEntities;//OK

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<GlassThicknessEntity> glassThicknessEntities;//OK

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<PiTypeEntity> PiTypeEntities;//OK

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<ConfirmThroughEntity>  confirmThroughEntities;//OK

    //@OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    //private  List<CompanyTypeEntity>  companyTypeEntities;//OK

/*    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<AddressEntity>  addressEntities;*/

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<ProFormaInvoiceEntity>  proFormaInvoiceEntities;//OK

    //@OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    //private  List<ProFormaInvoiceItemEntity>  proFormaInvoiceItemEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<StatusEntity>  statusEntities;//OK

    //@OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    //private  List<ServiceRateInvoiceEntity>  serviceRateInvoiceEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<AddressTypeEntity>  addressTypeEntities;//OK

    //@OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    //private  List<CompanyAddressEntity>  companyAddressEntities;

    public TenantValue toDTO() {
        return TenantValue.newBuilder()
                .tenantId(getId())
                .uuid(getUuid())
                .tenantName(getTenantName())
                .address(getAddress())
                .city(getCity())
                .state(getState())
                .countryId(getCountryId())
                .pinCode(getPinCode())
                .phoneNumber(getPhoneNumber())
                .emailId(getEmailId())
                .activationDate(getActivationDate())
                .expiryDate(getExpiryDate())
                .gracePeriod(getGracePeriod())
/*                .companyTypeValues(getCompanyEntities().stream()
                        .map(companyEntity -> companyEntity.getCompanyTypeEntity().toDTO())
                        .collect(Collectors.toList()))*/
/*                .additionalChargesValues(getAdditionalChargesEntities().stream()
                        .map(AdditionalChargesEntity::toDTO)
                        .collect(Collectors.toList()))*/
                // ... (other @OneToMany fields)
                .createdDttm(getCreatedDatetime())
                .updatedDttm(getLastUpdatedDatetime())
                .createdBy(getCreatedBy())
                .updatedBy(getLastUpdatedBy())
                .isActive(isActive())
                .build();
    }

}
