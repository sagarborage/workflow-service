package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.GatePassEntity;
import com.sowermate.tenantService.entities.minimal.GatePassDetailsInfoProjection;
import com.sowermate.tenantService.entities.minimal.GatePassInfoProjection;
import com.sowermate.tenantService.entities.value.GatePassDetailsInfo;
import com.sowermate.tenantService.entities.value.GatePassInfo;
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

    @Query("SELECT MAX(gp.gatePassNo) FROM GatePassEntity gp " +
            "JOIN gp.companyEntity c " +
            "WHERE c.uuid = :companyUuid")
    Integer findMaxGatePassNoByCompanyUuid(String companyUuid);

    @Query("SELECT " +
            "(SUM(p.optimizeBucket) + SUM(p.cuttingBucket) + SUM(p.toughenBucket) + " +
            "SUM(p.dispatchBucket) + SUM(p.gatePassBucket) + SUM(p.optimizeCompleted) + " +
            "SUM(p.cuttingCompleted) + SUM(p.toughenCompleted) + SUM(p.dispatchCompleted) + " +
            "SUM(p.gatePassCompleted) ) as totalQuantity, " +
            "SUM(p.gatePassCompleted) as dispatchedQuantity, " +
            "SUM(p.gatePassBucket) as gatePassBucket " +
            "FROM ProFormaInvoiceEntity pi " +
            "JOIN pi.firm c " +
            "JOIN pi.proFormaInvoiceItemEntities p " +
            "WHERE c.uuid = :companyUuid " +
            "AND pi.uuid = :proformaInvoiceUuid")
    GatePassInfoProjection findGatePassInfoByProformaInvoiceUuid(String companyUuid, String proformaInvoiceUuid);

    @Query("SELECT " +
            "g.uuid as gatePassUuid, " +
            "g.gatePassNo as gatePassNo , " +
            "sum(gd.gatePassQty) as quantity " +
            "FROM ProFormaInvoiceEntity p " +
            "JOIN p.firm c " +
            "JOIN p.gatePassEntities g "+
            "JOIN g.gatePassDetailsEntities gd "+
            "WHERE c.uuid = :companyUuid " +
            "AND p.uuid = :proformaInvoiceUuid " +
            "GROUP BY g.uuid ")
    List<GatePassDetailsInfoProjection> findGatePassDetailsInfoByProformaInvoiceUuid(String companyUuid, String proformaInvoiceUuid);


}
