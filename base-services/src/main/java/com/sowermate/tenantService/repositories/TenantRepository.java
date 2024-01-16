package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface TenantRepository extends JpaRepository <TenantEntity, String> {
   // @Query("SELECT t FROM TenantEntity t WHERE t.uuid = :tenantUuid")
    TenantEntity findByUuid(@Param("tenantUuid")String tenantUuid);

    @Transactional
    @Modifying
    @Query("UPDATE TenantEntity t SET t.isActive = false WHERE t.uuid = :tenantUuid")
    void softDelete(@Param("tenantUuid") String tenantUuid);
    /*@Modifying
    @Query("DELETE FROM TenantEntity t WHERE t.tenantUuid = :tenantUuid")
    public int deleteByTenantUuid(@Param("tenantUuid")String tenantUuid);
*/
}
