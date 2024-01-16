package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.RoleTypeValue;

import java.util.List;

public interface RoleTypeService {

    public RoleTypeValue createRoleType(RoleTypeValue RoleTypeValue);

    public RoleTypeValue editRoleType(RoleTypeValue RoleTypeValue);

    public RoleTypeValue getRoleType(String tenantUuid, String RoleTypeUuid);

    public RoleTypeValue deleteRoleType(String tenantUuid, String RoleTypeUuid);

    public List<RoleTypeValue> getAllRoleType(String tenantUuid);
}
