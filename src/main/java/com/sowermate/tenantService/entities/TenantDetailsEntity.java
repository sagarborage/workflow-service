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
@Table(name="tenant_details")
public class TenantDetailsEntity implements Serializable {

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

    @OneToMany(mappedBy="tenantDetailsEntity",cascade=CascadeType.ALL)
    private List<CompanyEntity> companyEntity;

    @OneToMany(mappedBy="tenantDetailsEntities",cascade=CascadeType.ALL)
    private List<AdditionalChargesEntity> additionalChargesEntity;

    @OneToMany(mappedBy="tenantDetailsEntity",cascade=CascadeType.ALL)
    private List<ServiceRateEntity> serviceRateEntity;

}
