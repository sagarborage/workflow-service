package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.GlassSpecificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface GlassSpecificationRepository extends JpaRepository<GlassSpecificationEntity, String> {
    public GlassSpecificationEntity findByGlassSpecificationUuid(@Param("glassSpecificationUuid")String glassSpecificationUuid);

    public GlassSpecificationEntity findByGlassSpecificationId(int glassSpecificationId);

    @Transactional
    @Modifying
    @Query("UPDATE GlassSpecificationEntity g SET g.isActive = false WHERE g.glassSpecificationUuid = :glassSpecificationUuid")
    void softDelete(@Param("glassSpecificationUuid") String glassSpecificationUuid);

}
