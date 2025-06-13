package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.GlassThicknessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GlassThicknessRepository extends JpaRepository<GlassThicknessEntity, String> {
    @Query("SELECT g FROM GlassThicknessEntity g " +
            "JOIN g.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND g.uuid = :glassThicknessUuid")
    public GlassThicknessEntity findByTenantEntity_UuidAndGlassThicknessUuid(@Param("tenantUuid") String tenantUuid, @Param("glassThicknessUuid") String glassThicknessUuid);

    public List<GlassThicknessEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    @Transactional
    @Modifying
    @Query("UPDATE GlassThicknessEntity g SET g.isActive = false WHERE g.uuid = :glassThicknessUuid")
    void softDelete(@Param("glassThicknessUuid") String glassThicknessUuid);

    @Query("SELECT g.name FROM " +
            "GlassThicknessEntity g " +
            "WHERE g.id = :id")
    String getFindGlassThickNess(Long id);
}
