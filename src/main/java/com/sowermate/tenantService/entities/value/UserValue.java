package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.UserEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserValue extends BaseValue {

    private String tenantUuid;
    private String roleTypeUuid;
    private String name;
    private String userName;
    private String password;
    private String salt;
    private String mobileNumber;
    private String emailId;

    public UserEntity toEntity() {
        return UserEntity.newBuilder()
                .uuid(getUuid())
                .name(getName())
                .userName(getUserName())
                .password(getPassword())
                .salt(getPassword())
                .mobileNumber(getMobileNumber())
                .emailId(getEmailId())
                .isActive(isActive())
                .build();
    }
}
