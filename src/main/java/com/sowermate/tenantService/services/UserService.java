package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.UserValue;

import java.util.List;

public interface UserService {

    public UserValue createUser(UserValue glassTypeValue);

    public UserValue editUser(UserValue glassTypeValue);

    public UserValue getUser(String tenantUuid, String glassTypeUuid);

    public UserValue deleteUser(String tenantUuid, String glassTypeUuid);

    public List<UserValue> getAllUser(String tenantUuid);
}
