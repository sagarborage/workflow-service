package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.RoleTypeEntity;
import com.sowermate.tenantService.entities.UserEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.minimal.UserAuthProjection;
import com.sowermate.tenantService.entities.value.UserValue;
import com.sowermate.tenantService.repositories.RoleTypeRepository;
import com.sowermate.tenantService.repositories.UserRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleTypeRepository roleTypeRepository;
    @Autowired
    TenantRepository tenantRepository;

    @Override
    public UserValue createUser(UserValue userValue) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(userValue.getTenantUuid());
        RoleTypeEntity roleTypeEntity = roleTypeRepository.findByTenantEntity_UuidAndRoleTypeUuid(userValue.getTenantUuid(), userValue.getRoleTypeUuid());
        UserEntity userEntity = userValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .roleTypeEntity(roleTypeEntity)
                .build();
        return userRepository.save(userEntity).toDTO().toBuilder().tenantUuid(tenantEntity.getUuid()).roleTypeUuid(roleTypeEntity.getUuid()).build();
    }

    @Override
    public UserValue editUser(UserValue userValue) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(userValue.getTenantUuid());
        RoleTypeEntity roleTypeEntity = roleTypeRepository.findByTenantEntity_UuidAndRoleTypeUuid(userValue.getTenantUuid(), userValue.getRoleTypeUuid());
        UserEntity tempUserEntity = userRepository.findByTenantEntity_UuidAndUserUuid(userValue.getTenantUuid(),
                userValue.getUuid());
        UserEntity userEntity = userValue.toEntity().toBuilder()
                .id(tempUserEntity.getId())
                .tenantEntity(tenantEntity)
                .roleTypeEntity(roleTypeEntity)
                .createdDateTime(tempUserEntity.getCreatedDateTime())
                .createdBy(tempUserEntity.getCreatedBy())
                .build();
        return userRepository.save(userEntity).toDTO().toBuilder().tenantUuid(tenantEntity.getUuid()).roleTypeUuid(roleTypeEntity.getUuid()).build();
    }

    @Override
    public UserValue getUser(String tenantUuid, String UserUuid) {
        return userRepository.findByTenantEntity_UuidAndUserUuid(tenantUuid, UserUuid).toDTO();
    }

    @Override
    public UserValue deleteUser(String tenantUuid, String UserUuid) {
        userRepository.softDelete(UserUuid);
        UserEntity userEntity = userRepository.findByTenantEntity_UuidAndUserUuid(tenantUuid, UserUuid);
        return userEntity.toDTO();
    }

    @Override
    public List<UserValue> getAllUser(String tenantUuid) {
        List<UserEntity> UserEntities = userRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return userRepository.findAllByTenantEntity_Uuid(tenantUuid).stream().map(gte -> gte.toDTO()).collect(Collectors.toList());
    }

    @Override
    public UserAuthProjection userAuthentication(String userName, String password) {
        UserAuthProjection userAuthProjection = userRepository.userAuthentication(userName, password);
        return userAuthProjection;
    }
}

