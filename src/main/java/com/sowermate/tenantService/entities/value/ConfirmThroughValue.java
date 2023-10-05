package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfirmThroughValue extends BaseValue {
    private Long confirmThroughId;
    private String confirmThroughUuid;
    private String tenantUuid;
    private String name;
    private ProFormaInvoiceValue proFormaInvoice;

    public ConfirmThroughEntity toEntity() {
        return ConfirmThroughEntity.newBuilder()
                .id(getConfirmThroughId())
                .uuid(getConfirmThroughUuid())
                .name(getName())
                .proFormaInvoiceEntity(getProFormaInvoice().toEntity())
                .build();
    }
}
