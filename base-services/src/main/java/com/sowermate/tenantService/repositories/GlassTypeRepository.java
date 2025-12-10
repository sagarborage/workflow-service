package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.GlassTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface GlassTypeRepository extends JpaRepository <GlassTypeEntity ,String> {

    @Query("SELECT g FROM GlassTypeEntity g " +
            "JOIN g.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND g.uuid = :glassTypeUuid")
    public GlassTypeEntity findByTenantEntity_UuidAndGlassTypeUuid(String tenantUuid, String glassTypeUuid);

    public List<GlassTypeEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    @Transactional
    @Modifying
    @Query("UPDATE GlassTypeEntity g SET g.isActive = false WHERE g.uuid = :glassTypeUuid")
    void softDelete(@Param("glassTypeUuid") String glassTypeUuid);

}
