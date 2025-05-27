package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.AdditionalChargesEntity;
import com.sowermate.workflow.domain.entities.ConfirmThroughEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmThroughRepository extends JpaRepository<ConfirmThroughEntity, String> {

    //  public ConfirmThroughEntity findByConfirmThroughUuid(@Param("confirmThroughUuid")String confirmThroughUuid);

    @Query("SELECT c FROM ConfirmThroughEntity c " +
            "JOIN c.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND c.uuid = :confirmThroughUuid")
    public ConfirmThroughEntity findByTenantEntity_UuidAndConfirmThroughUuid(@Param("tenantUuid") String tenantUuid, @Param("confirmThroughUuid") String confirmThroughUuid);

    public List<ConfirmThroughEntity> findAllByTenantEntity_Uuid(String tenantUuid);


    @Modifying
    @Query("DELETE FROM ConfirmThroughEntity c WHERE c.uuid = :confirmThroughUuid")
    int deleteByConfirmThroughUuid(@Param("confirmThroughUuid") String confirmThroughUuid);


}
