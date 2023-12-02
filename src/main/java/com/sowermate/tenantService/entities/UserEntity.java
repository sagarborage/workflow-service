package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.common.Base;
import com.sowermate.tenantService.entities.value.UserValue;
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
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class UserEntity extends Base {

    @Column(name = "name")
    private String name;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email_id")
    private String emailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleTypeEntity roleTypeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenantEntity;

    public UserValue toDTO() {
        return UserValue.newBuilder()
                .id(getId())
                .uuid(getUuid())
                .roleTypeUuid(getRoleTypeEntity() != null ? getRoleTypeEntity().getUuid() : null)
                .name(getName())
                .userName(getUserName())
                .password(getPassword())
                .salt(getPassword())
                .mobileNumber(getMobileNumber())
                .emailId(getEmailId())
                .isActive(getIsActive())
                .build();
    }
}
