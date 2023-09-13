package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.GlassSpecificationEntity;
import com.sowermate.tenantService.entities.GlassThicknessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface GlassSpecificationRepository extends JpaRepository<GlassSpecificationEntity, String> {
    @Query("SELECT g FROM GlassSpecificationEntity g " +
            "JOIN g.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND g.uuid = :glassSpecificationUuid")
    public GlassSpecificationEntity findByTenantEntity_UuidAndGlassSpecificationUuid(@Param("tenantUuid") String tenantUuid, @Param("glassSpecificationUuid") String glassSpecificationUuid);

    public List<GlassSpecificationEntity> findAllByTenantEntity_Uuid(String tenantUuid);


    @Transactional
    @Modifying
    @Query("UPDATE GlassSpecificationEntity g SET g.isActive = false WHERE g.uuid = :glassSpecificationUuid")
    void softDelete(@Param("glassSpecificationUuid") String glassSpecificationUuid);

}
