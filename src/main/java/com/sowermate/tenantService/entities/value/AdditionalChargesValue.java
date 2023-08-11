package com.sowermate.tenantService.entities.value;

import lombok.Data;

import java.util.Date;

@Data
public class    AdditionalChargesValue   {
    private String tenantUuid;
    private String additionalChargesUuid;
    private Date createdDttm;
    private Date updatedDttm;
    private float extraMm;
    private float insurance;
    private float adminCharges;
    private float forwardingCharges;
    private float gst;
    private String createdBy;
    private String updatedBy;

}