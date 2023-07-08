package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class AddressTypeValue {
    private String  addressTypeUuid;
    private String type;
    private String description;
    private Boolean isActive;
    private String tenantUuid;
}
