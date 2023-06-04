package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.GlassThicknessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface GlassThicknessRepository  extends JpaRepository<GlassThicknessEntity, String> {
    public List<GlassThicknessEntity> findByUuid(@Param("uuid")String uuid);

    public List<GlassThicknessEntity> findByGlassThicknessId(int glassThicknessId);

    @Transactional
    @Modifying
    @Query("UPDATE GlassThicknessEntity g SET g.isActive = false WHERE g.glassThicknessId = :id")
    void softDelete(@Param("id") int glassThicknessId);

}
