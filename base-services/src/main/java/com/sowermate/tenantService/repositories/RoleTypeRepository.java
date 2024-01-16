package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.RoleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoleTypeRepository extends JpaRepository <RoleTypeEntity ,String> {

    @Query("SELECT r FROM RoleTypeEntity r " +
            "JOIN r.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND r.uuid = :roleTypeUuid")
    public RoleTypeEntity findByTenantEntity_UuidAndRoleTypeUuid(String tenantUuid, String roleTypeUuid);

    public List<RoleTypeEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    @Transactional
    @Modifying
    @Query("UPDATE RoleTypeEntity r SET r.isActive = false WHERE r.uuid = :RoleTypeUuid")
    void softDelete(@Param("RoleTypeUuid") String RoleTypeUuid);

}
