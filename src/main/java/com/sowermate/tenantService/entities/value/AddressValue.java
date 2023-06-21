package com.sowermate.tenantService.entities.value;

import lombok.Data;

import java.util.Date;

@Data
public class AddressValue {
    private String addressUuid;
    private Date createdDttm;
    private Date updatedDttm;
    private String  addressLine1;
    private  String addressLine2;
    private  String addressLine3;
    private  String city;
    private  int stateCode;
    private  int countryCode;
    private  int pinCode;
    private  String workPhone;
    private  String fax;
    private  int primaryPhoneNumber;
    private  int alternatePhoneNumber;
    private  String email;
    private  String website;
    private  String createdBy;
    private  String updatedBy;
    private Boolean isActive;

}
