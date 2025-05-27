package com.sowermate.workflow.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.core.base.dtos.BaseDto;
import com.sowermate.workflow.domain.entities.AdditionalChargesEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalChargesValue extends BaseDto {
//    private Long additionalChargesId;
//    private String additionalChargesUuid;
    private String tenantUuid;
    private float extraMm;
    private float insurance;
    private float adminCharges;
    private float forwardingCharges;
    private float gst;


    public AdditionalChargesEntity toEntity() {
        return AdditionalChargesEntity.newBuilder()
//                .id(getAdditionalChargesId())
//                .uuid(getAdditionalChargesUuid())
                .uuid(getUuid())
                .extraMm(getExtraMm())
                .insurance(getInsurance())
                .adminCharges(getAdminCharges())
                .forwardingCharges(getForwardingCharges())
                .gst(getGst())
                //.tenantEntity(getTenantValue().toEntity())
                .isActive(getIsActive())
                .createdBy(getCreatedBy())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedBy(getLastUpdatedBy())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .version(getVersion())
                .build();
    }
}