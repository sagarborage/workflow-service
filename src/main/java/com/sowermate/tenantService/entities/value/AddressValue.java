package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressValue {
    private String addressUuid;
    private String tenantUuid;
    private String tenantId;
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
    private  String primaryPhoneNumber;
    private  String alternatePhoneNumber;
    private  String email;
    private  String website;
    private  String createdBy;
    private  String updatedBy;
    private Boolean isActive;

}
