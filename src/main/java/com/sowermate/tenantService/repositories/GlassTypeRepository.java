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

    public List<GlassTypeEntity> findByUuid(@Param("uuid")String uuid);

    public List<GlassTypeEntity> findByGlassTypeId(int glassTypeId);

    @Transactional
    @Modifying
    @Query("UPDATE GlassTypeEntity g SET g.isActive = false WHERE g.uuid = :uuid")
    void softDelete(@Param("uuid") String uuid);

}
