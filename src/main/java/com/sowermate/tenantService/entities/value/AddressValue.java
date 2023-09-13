package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.AddressEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressValue extends BaseValue {
    @JsonIgnore
    private Long addressId;
    private String addressUuid;
    private String addressTypeUuid;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private int stateCode;
    private int countryCode;
    private int pinCode;
    private String workPhone;
    private String fax;
    private String primaryPhoneNumber;
    private String alternatePhoneNumber;
    private String email;
    private String website;

    public AddressEntity toEntity() {
        return AddressEntity.newBuilder()
                .id(getAddressId())
                .uuid(getAddressUuid())
                .addressLine1(getAddressLine1())
                .addressLine2(getAddressLine2())
                .addressLine3(getAddressLine3())                .isActive(isActive())
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
                .createdDatetime(getCreatedDttm())
                .lastUpdatedDatetime(getUpdatedDttm())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getUpdatedBy())
                .build();
    }
}
