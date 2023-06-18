package com.sowermate.tenantService.entities.value;

import lombok.Data;

@Data
public class AdditionalChargesValue extends  CommonValue  {
    private String uuid;
    private float extraMm;
    private float insurance;
    private float adminCharges;
    private float forwardingCharges;
    private float gst;
    private String createdBy;
    private String updatedBy;
    private String tenantUUID;

}