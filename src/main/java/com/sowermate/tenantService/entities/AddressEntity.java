package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.CommonEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name="address")
public class AddressEntity extends CommonEntity {
    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id", unique = true, nullable = false, updatable = false)
     int addressId;

    @Column(name = "address_line1")
    private String  addressLine1;

    @Column(name="address_line2")
    private  String addressLine2;

    @Column(name="address_line3")
    private  String addressLine3;

    @Column(name = "city")
    private  String city;

    @Column(name = "state_code")
    private  int stateCode;

    @Column (name = "country_code")
    private  int countryCode;

    @Column (name = "pin_code")
    private  int pinCode;

    @Column (name = "work_phone")
    private  String workPhone;

    @Column (name = "fax")
    private  String fax;

    @Column (name= "primary_phone_number")
    private  int primaryPhoneNumber;

    @Column(name = "alternate_phone_number")
    private  int alternatePhoneNumber;

    @Column(name =  "email")
    private  String email;

    @Column(name= "website")
    private  String website;

    @Column (name = "created_by")
    private  String createdBy;

    @Column (name = "updated_by")
    private  String updatedBy;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive = true;


    @OneToMany(mappedBy="addressEntity",cascade=CascadeType.ALL)
    private List<CompanyEntity> companyEntity;
}
