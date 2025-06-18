package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.GatePassEntity;
import com.sowermate.tenantService.entities.minimal.GatePassDetailsInfoProjection;
import com.sowermate.tenantService.entities.minimal.GatePassInfoProjection;
import com.sowermate.tenantService.entities.minimal.GatePassProjection;
import com.sowermate.tenantService.entities.minimal.GlassInfoProjection;
import com.sowermate.tenantService.services.PiInfoProjectionForReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GatePassRepository extends JpaRepository<GatePassEntity, String> {
    GatePassEntity findByUuid(String uuid);

    @Query("SELECT gp FROM GatePassEntity gp " +
            "JOIN gp.tenantEntity t " +
            "JOIN gp.companyEntity c " +
            "WHERE t.uuid = :tenantUuid " +
            "AND gp.uuid = :gatePassUuid " +
            "AND c.uuid = :companyUuid ")
   GatePassEntity findByGatePassUuidAndTenantUuid(String gatePassUuid,String tenantUuid,String companyUuid);

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
            "SUM(p.quantity) as totalQuantity, " +
            "SUM(p.gatePassCompleted) as dispatchedQuantity, " +
            "SUM(p.gatePassBucket) as gatePassBucket " +
            "FROM ProFormaInvoiceEntity pi " +
            "JOIN pi.firm c " +
            "JOIN pi.proFormaInvoiceItemEntities p " +
            "WHERE c.uuid = :companyUuid " +
            "AND pi.uuid = :proformaInvoiceUuid " +
            "GROUP BY pi.uuid ")
    GatePassInfoProjection findGatePassInfoByProformaInvoiceUuid(String companyUuid, String proformaInvoiceUuid);

    @Query("SELECT " +
            "p.companyIdBill.companyName as partyName, " +
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

    @Query("SELECT " +
            "gs.name as glassSpecification , " +
            "gt.name as thickness, " +
            "sum(pii.quantity) as totalItemQty, "+
            "sum(gd.gatePassQty) as gatePassItemQty "+
            "FROM GatePassEntity g " +
            "JOIN g.gatePassDetailsEntities gd "+
            "JOIN gd.proFormaInvoiceItemEntity pii "+
            "JOIN pii.glassSpecificationEntity gs "+
            "JOIN pii.glassThicknessEntity gt "+
            "Where g.uuid = :gatePassUuid " +
            "GROUP BY gs.name, gt.name")
    List<GlassInfoProjection> findGlassItemsInfoByGatePassUuid(String gatePassUuid);


    @Query("SELECT " +
            "p.piNumber as piNo, " +
            "p.invoiceDate as piDate, " +
            "f.companyName as partyName, " +
            "cb.companyName as partyBillToName, " +
            "cb.uuid as partyBillToUuid " +
            "FROM GatePassEntity g " +
            "JOIN g.proFormaInvoiceEntity p "+
            "JOIN p.firm f "+
            "JOIN p.companyIdBill cb "+
            "Where g.uuid = :gatePassUuid ")
    PiInfoProjectionForReport findPiItemsInfoByGatePassUuid(String gatePassUuid);

 @Query("SELECT " +
         "g.uuid as uuid, " +
         "t.uuid as tenantUuid, " +
         "c.uuid as companyUuid, " +
         "f.uuid as firmUuid, " +
         "g.gatePassNo as gatePassNo, " +
         "g.createdDateTime as dateTime, " +
         "pi.piNumber as piNumber, " +
         "f.companyName as companyName, " +
         "pi.tenantEntity.tenantName as partyName, " +
         "CONCAT(g.vehicleNo, '/', g.driverName, '/', g.driverContactNo) as vehicleDetails, " +
         "SUM(gd.gatePassQty) as quantity " +
         "FROM GatePassEntity g " +
         "JOIN g.gatePassDetailsEntities gd " +
         "JOIN g.tenantEntity t " +
         "JOIN g.companyEntity c " +
         "JOIN g.proFormaInvoiceEntity pi " +
         "JOIN g.partyCompanyEntity f " +
         "WHERE t.uuid = :tenantUuid " +
         "AND g.createdDateTime BETWEEN :fromDate AND :toDate " +
         "AND (:companyUuid IS NULL OR c.uuid = :companyUuid) " +
         "AND (:firmUuid IS NULL OR f.uuid = :firmUuid) " +
         "GROUP BY g.uuid")
 List<GatePassProjection> findGatePassDetailsWithProformaDetails(
         @Param("tenantUuid") String tenantUuid,
         @Param("companyUuid") String companyUuid,
         @Param("firmUuid") String firmUuid,
         @Param("fromDate") LocalDateTime fromDate,
         @Param("toDate") LocalDateTime toDate);
}
