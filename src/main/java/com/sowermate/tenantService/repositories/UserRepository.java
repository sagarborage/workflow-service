package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.UserEntity;
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

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.isActive = false WHERE u.uuid = :userUuid")
    void softDelete(@Param("userUuid") String userUuid);

}
