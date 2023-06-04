package com.sowermate.tenantService.entities.value;

import lombok.Data;

import java.util.Date;

@Data
public class TenantDetailsValue {

    private  int tenantId;
    private String uuid;
    private  String tenantName;
    private String  address;
    private  String city;
    private  String state;
    private  int countryId;
    private  String pinCode;
    private  String phoneNumber;
    private  String emailId;
    private Date activationDate;
    private  Date expiryDate;
    private  int gracePeriod;
    private Boolean isActive;

}
