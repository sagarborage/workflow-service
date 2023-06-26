package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.AdditionalChargesEntity;
import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import com.sowermate.tenantService.entities.ServiceRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalChargesRepository  extends JpaRepository<AdditionalChargesEntity ,String> {



    @Query("SELECT a FROM AdditionalChargesEntity a " +
            "JOIN a.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND a.additionalChargesUuid = :additionalChargesUuid")
    public AdditionalChargesEntity findByTenantEntity_UuidAndAdditionalChargesUuid(@Param("tenantUuid") String tenantUuid, @Param("additionalChargesUuid") String additionalChargesUuid);

    public List<AdditionalChargesEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    public AdditionalChargesEntity deleteByAdditionalChargesUuid(@Param("additionalChargesUuid")String additionalChargesUuid);




}
