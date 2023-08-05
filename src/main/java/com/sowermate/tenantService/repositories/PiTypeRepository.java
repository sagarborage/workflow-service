package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.PiTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface PiTypeRepository extends JpaRepository<PiTypeEntity, String> {


    @Query("SELECT p FROM PiTypeEntity p " +
            "JOIN p.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND p.piTypeUuid = :piTypeUuid")
    public PiTypeEntity findByTenantEntity_UuidAndPiTypeUuid(@Param("tenantUuid") String tenantUuid, @Param("piTypeUuid") String piTypeUuid);


    /*    public List<PiTypeEntity> findByTenantEntity_UuidAndPiTypeUuid(@Param("uuid")String uuid);*/
    @Transactional
    @Modifying
    @Query("UPDATE PiTypeEntity p SET p.isActive = false WHERE p.piTypeUuid = :piTypeUuid")
    void softDelete(@Param("piTypeUuid") String piTypeUuid);

    public List<PiTypeEntity> findAllByTenantEntity_Uuid(String tenantUuid);

}
