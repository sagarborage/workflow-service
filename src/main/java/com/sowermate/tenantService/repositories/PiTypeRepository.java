package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import com.sowermate.tenantService.entities.PiTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PiTypeRepository extends JpaRepository<PiTypeEntity, String> {


    @Query("SELECT p FROM PiTypeEntity p " +
            "JOIN p.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND p.piTypeUuid = :piTypeUuid")
    public PiTypeEntity findByTenantEntity_UuidAndPiTypeUuid(@Param("tenantUuid") String tenantUuid, @Param("piTypeUuid") String piTypeUuid);

    public List<PiTypeEntity> findAllByTenantEntity_Uuid(String tenantUuid);
    @Modifying
    @Query("DELETE FROM PiTypeEntity p WHERE p.piTypeUuid = :piTypeUuid")
    public int deleteByPiTypeUuid(@Param("piTypeUuid")String piTypeUuid);

}
