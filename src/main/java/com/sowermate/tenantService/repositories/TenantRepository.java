package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.TenantDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface TenantRepository extends JpaRepository <TenantDetailsEntity, String> {
    @Query("SELECT t FROM TenantDetailsEntity t WHERE t.uuid = :uuid")
    public List<TenantDetailsEntity> findByUuid(@Param("uuid")String uuid);

    public List<TenantDetailsEntity> findByTenantId(int tenantId);


   // public List<TenantDetailsEntity> deleteTenantByUuid(@Param("uuid")String uuid);

    @Transactional
    @Modifying
    @Query("UPDATE TenantDetailsEntity t SET t.isActive = false WHERE t.uuid = :uuid")
    void softDelete(@Param("uuid") String uuid);

}
