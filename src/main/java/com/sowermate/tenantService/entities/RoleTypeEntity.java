package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.RoleTypeValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "role_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class RoleTypeEntity extends Base {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "roleTypeEntity", fetch = FetchType.LAZY)
    private List<UserEntity> userEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenantEntity;

    public RoleTypeValue toDTO() {
        return RoleTypeValue.newBuilder()
                .id(getId())
                .uuid(getUuid())
                .name(getName())
                .isActive(getIsActive())
                .build();
    }
}
