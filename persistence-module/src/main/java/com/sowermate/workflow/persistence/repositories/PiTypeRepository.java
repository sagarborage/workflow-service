package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.PiTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PiTypeRepository extends JpaRepository<PiTypeEntity, String> {

    //public List<PiTypeEntity> findByPiTypeUuid(@Param("uuid")String uuid);
    @Query("SELECT p FROM PiTypeEntity p " +
            "JOIN p.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND p.uuid = :piTypeUuid")
    public PiTypeEntity findByTenantEntity_UuidAndPiTypeUuid(@Param("tenantUuid") String tenantUuid, @Param("piTypeUuid") String piTypeUuid);

    public List<PiTypeEntity> findAllByTenantEntityUuid(String tenantUuid);

    /*    public List<PiTypeEntity> findByTenantEntity_UuidAndPiTypeUuid(@Param("uuid")String uuid);*/
    @Modifying
    @Query("DELETE FROM PiTypeEntity p WHERE p.uuid = :piTypeUuid")
    public int deleteByUuid(@Param("piTypeUuid") String piTypeUuid);

}
