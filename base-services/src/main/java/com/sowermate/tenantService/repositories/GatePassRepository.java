package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.GatePassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatePassRepository extends JpaRepository<GatePassEntity, String> {
    GatePassEntity findByUuid(String uuid);

    @Query("SELECT gp FROM GatePassEntity gp " +
            "JOIN gp.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND gp.uuid = :gatePassUuid")
   GatePassEntity findByGatePassUuidAndTenantUuid(String gatePassUuid,String tenantUuid);

    @Query("SELECT gp FROM GatePassEntity gp " +
            "JOIN gp.tenantEntity t " +
            "JOIN gp.companyEntity c " +
            "WHERE t.uuid = :tenantUuid " +
            "AND c.uuid = :companyUuid")
    List<GatePassEntity> findByTenantUuidAndCompanyUuid(String tenantUuid,String companyUuid);
}
