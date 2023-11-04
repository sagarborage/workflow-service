package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.AdditionalChargesEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalChargesValue extends BaseValue {
    private Long additionalChargesId;
    private String additionalChargesUuid;
    private String tenantUuid;
    private float extraMm;
    private float insurance;
    private float adminCharges;
    private float forwardingCharges;
    private float gst;


    public AdditionalChargesEntity toEntity() {
        return AdditionalChargesEntity.newBuilder()
                .id(getAdditionalChargesId())
                .uuid(getAdditionalChargesUuid())
                .extraMm(getExtraMm())
                .insurance(getInsurance())
                .adminCharges(getAdminCharges())
                .forwardingCharges(getForwardingCharges())
                .gst(getGst())
                //.tenantEntity(getTenantValue().toEntity())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}