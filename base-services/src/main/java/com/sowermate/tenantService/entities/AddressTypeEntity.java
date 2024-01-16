package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.AddressTypeValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "address_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class AddressTypeEntity extends Base {

    private static final long serialVersionUID = 3981140897718611608L;

    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "addressType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public AddressTypeValue toDTO() {
        return AddressTypeValue.newBuilder()
                .addressTypeId(getId())
                .addressTypeUuid(getUuid())
                .type(getType())
                .description(getDescription())
                //.tenantValue(getTenantEntity().toDTO())
                .createdDateTime(getCreatedDateTime())
                .lastUpdatedDateTime(getLastUpdatedDateTime())
                .createdBy(getCreatedBy())
                .lastUpdatedBy(getLastUpdatedBy())
                .isActive(getIsActive())
                .build();
    }
}
