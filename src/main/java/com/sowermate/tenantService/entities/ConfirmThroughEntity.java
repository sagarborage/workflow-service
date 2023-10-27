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

    @OneToOne(mappedBy="confirmThroughEntity",cascade=CascadeType.ALL)
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public ConfirmThroughValue toDTO() {
        return ConfirmThroughValue.newBuilder()
                .uuid(getUuid())
                .name(getName())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(isActive())
                .build();
    }
}
