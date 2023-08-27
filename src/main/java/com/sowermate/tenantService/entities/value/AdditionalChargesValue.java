package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.AdditionalChargesEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalChargesValue   {
    private Integer additionalChargesId;
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
    private TenantValue tenantValue;

    public AdditionalChargesEntity toEntity() {
        return AdditionalChargesEntity.newBuilder()
                .additionalChargesId(getAdditionalChargesId())
                .additionalChargesUuid(getAdditionalChargesUuid())
                .createdDttm(getCreatedDttm())
                .updatedDttm(getUpdatedDttm())
                .extraMm(getExtraMm())
                .insurance(getInsurance())
                .adminCharges(getAdminCharges())
                .forwardingCharges(getForwardingCharges())
                .gst(getGst())
                .tenantEntity(getTenantValue().toEntity())
                .createdBy(getCreatedBy())
                .updatedBy(getUpdatedBy())
                .build();
    }
}