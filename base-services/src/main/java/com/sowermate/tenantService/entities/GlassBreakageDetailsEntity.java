package com.sowermate.tenantService.entities;

import com.sowermate.base.entities.Base;
import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import com.sowermate.tenantService.entities.value.GlassBreakageDetailsValue;
import com.sowermate.tenantService.enums.DeptNameEnum;
import com.sowermate.tenantService.enums.ProformaInvoiceStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "glass_breakage_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class GlassBreakageDetailsEntity extends Base {

    private static final long serialVersionUID = -241370177952331642L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="proforma_invoice_id")
    private ProFormaInvoiceEntity proFormaInvoiceEntity;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="proforma_invoice_item_id")
    private ProFormaInvoiceItemEntity proFormaInvoiceItemEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "dept_name")
    private DeptTypeEnum deptName;

    @Column(name = "details")
    private String details;

    public GlassBreakageDetailsValue toDTO() {
        return GlassBreakageDetailsValue.newBuilder()
                .uuid(getUuid())
                .proFormaInvoiceUuid(proFormaInvoiceEntity.getUuid())
                .proFormaInvoiceItemUuid(proFormaInvoiceItemEntity.getUuid())
                .tenantUuid(tenantEntity.getUuid())
                .deptName(getDeptName())
                .details(getDetails())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
