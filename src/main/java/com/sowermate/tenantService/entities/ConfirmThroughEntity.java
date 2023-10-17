package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
@Entity
@Getter
@Setter
@Table(name = "confirm_through")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ConfirmThroughEntity extends Base {

    private static final long serialVersionUID = -241370177952331642L;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_forma_invoice_id")
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firm_id")
    private  CompanyEntity firmEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

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

    public ConfirmThroughValue toDTO() {
        return ConfirmThroughValue.newBuilder()
                .uuid(getUuid())
                .name(getName())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
