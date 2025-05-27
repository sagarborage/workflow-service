package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.AdditionalChargesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalChargesRepository extends JpaRepository<AdditionalChargesEntity, String> {

    @Query("SELECT a FROM AdditionalChargesEntity a " +
            "JOIN a.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND a.uuid = :additionalChargesUuid")
    public AdditionalChargesEntity findByTenantEntity_UuidAndAdditionalChargesUuid(@Param("tenantUuid") String tenantUuid, @Param("additionalChargesUuid") String additionalChargesUuid);

    public List<AdditionalChargesEntity> findByTenantEntityId(long tenantId);

    /*  @Modifying
      @Query("DELETE FROM AdditionalChargesEntity a WHERE a.tenantUuid = :tenantUuid AND a.additionalChargesUuid = :additionalChargesUuid")
      void deleteByAdditionalChargesUuid(@Param("tenantUuid") String tenantUuid, @Param("additionalChargesUuid") String additionalChargesUuid);
  */
    @Modifying
    @Query("DELETE FROM AdditionalChargesEntity a WHERE a.uuid = :additionalChargesUuid")
    int deleteByAdditionalChargesUuid(@Param("additionalChargesUuid") String additionalChargesUuid);

}
