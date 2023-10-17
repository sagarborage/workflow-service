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
    private String tenantUuid;
    private String proFormaInvoiceUuid;
    private String firmUuid;
    private String name;

    public ConfirmThroughEntity toEntity() {
        return ConfirmThroughEntity.newBuilder()
                .uuid(getUuid())
                .name(getName())
                .isActive(getIsActive())
                .build();
    }

    //    CREATE TABLE confirm_through (
//            id int(10) NOT NULL AUTO_INCREMENT,
//    uuid varchar(36) NOT NULL DEFAULT (UUID()),
//    tenant_id int(10) NOT NULL,
//    firm_id int(10) NOT NULL,
//    pro_forma_invoice_id int(10) NOT NULL,
//    name varchar(50) DEFAULT NULL,
//    created_dttm datetime DEFAULT NULL,
//    last_updated_dttm datetime DEFAULT NULL,
//    created_by varchar(100) DEFAULT NULL,
//    last_updated_by varchar(100) DEFAULT NULL,
//    is_active boolean DEFAULT 1,
//    PRIMARY KEY (id) USING BTREE,
//    KEY FK_confirm_through_pro_forma_invoice (pro_forma_invoice_id) USING BTREE,
//    CONSTRAINT confirm_through_tenant_ibfk_1 FOREIGN KEY (tenant_id) REFERENCES tenant (id)
//            );
}
