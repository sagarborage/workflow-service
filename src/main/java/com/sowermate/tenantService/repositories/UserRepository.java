package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.UserEntity;
import com.sowermate.tenantService.entities.minimal.UserAuthProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <UserEntity,String> {

    @Query("SELECT u FROM UserEntity u " +
            "JOIN u.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND u.uuid = :userUuid")
    public UserEntity findByTenantEntity_UuidAndUserUuid(String tenantUuid, String userUuid);

    public List<UserEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    @Query("Select u.userName as userName,u.uuid as uuid, r.name as roleName from UserEntity u Join u.roleTypeEntity r where u.userName = :userName and u.password = :password")
    public UserAuthProjection userAuthentication(String userName, String password);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.isActive = false WHERE u.uuid = :userUuid")
    void softDelete(@Param("userUuid") String userUuid);

}
