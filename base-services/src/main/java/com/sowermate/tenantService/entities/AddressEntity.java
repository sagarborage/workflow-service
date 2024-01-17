package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.AddressValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class AddressEntity extends Base {

    private static final long serialVersionUID = -241370177952331642L;

    @Column(name = "address_line1")
    private String  addressLine1;

    @Column(name="address_line2")
    private  String addressLine2;

    @Column(name="address_line3")
    private  String addressLine3;

    @Column(name = "city")
    private  String city;

    @Column(name = "state_code")
    private  String stateCode;

    @Column (name = "country_code")
    private  String countryCode;

    @Column (name = "pin_code")
    private  String pinCode;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_type_id")
    private AddressTypeEntity addressType;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    public AddressValue toDTO() {
        return AddressValue.newBuilder()
                .addressId(getId())
                .addressUuid(getUuid())
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
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .addressTypeUuid(addressType.getUuid())
                .build();
    }

}
