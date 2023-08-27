package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.CommonEntity;
import com.sowermate.tenantService.entities.value.AddressValue;
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
@ToString(callSuper = true)
@Table(name = "address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @Column(name="uuid", unique=true, updatable=false)
    private String addressUuid;

    @Column(name="created_dttm")
    private Date createdDttm;

    @Column(name="updated_dttm")
    private Date updatedDttm;

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
    private  String primaryPhoneNumber;

    @Column(name = "alternate_phone_number")
    private  String alternatePhoneNumber;

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
    private List<CompanyAddressEntity> companyAddresses;

    public AddressValue toDTO() {
        return AddressValue.newBuilder()
                .addressId(getAddressId())
                .addressUuid(getAddressUuid())
                .createdDttm(getCreatedDttm())
                .updatedDttm(getUpdatedDttm())
                .addressLine1(getAddressLine1())
                .addressLine2(getAddressLine2())
                .addressLine3(getAddressLine3())
                .city(getCity())
                .stateCode(getStateCode())
                .countryCode(getCountryCode())
                .pinCode(getPinCode())
                .workPhone(getWorkPhone())
                .fax(getFax())
                .primaryPhoneNumber(getPrimaryPhoneNumber())
                .alternatePhoneNumber(getAlternatePhoneNumber())
                .email(getEmail())
                .website(getWebsite())
                .createdBy(getCreatedBy())
                .updatedBy(getUpdatedBy())
                .isActive(getIsActive())
                .companyAddressValues(getCompanyAddresses().stream().map(a->a.toDTO()).collect(Collectors.toList()))
                .build();
    }

}
