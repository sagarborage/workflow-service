package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class AddressValue extends  CommonValue{
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
