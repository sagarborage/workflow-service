package com.sowermate.tenantService.entities;


import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name="tenant")
public class TenantEntity implements Serializable {

    private static final long serialVersionUID = -241370177952331642L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="tenant_id",unique = true, nullable = false, updatable = false)
    private int tenantId;
    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String uuid;
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
    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private List<CompanyEntity> companyEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private List<AdditionalChargesEntity> additionalChargesEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private List<ServiceRateEntity> serviceRateEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private  List<GlassTypeEntity> glassTypeEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private  List<GlassThicknessEntity> glassThicknessEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private  List<PiTypeEntity> PiTypeEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private  List<ConfirmThroughEntity>  confirmThroughEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private  List<CompanyTypeEntity>  companyTypeEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private  List<AddressEntity>  addressEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private  List<ProFormaInvoiceEntity>  proFormaInvoiceEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private  List<ProFormaInvoiceItemEntity>  proFormaInvoiceItemEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private  List<StatusEntity>  statusEntities;

    @OneToMany(mappedBy="tenantEntity", cascade=CascadeType.ALL)
    private  List<ServiceRateInvoiceEntity>  serviceRateInvoiceEntities;


}
