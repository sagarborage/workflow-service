package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.GatePassEntity;
import com.sowermate.workflow.domain.entities.minimal.GatePassDetailsInfoProjection;
import com.sowermate.workflow.domain.entities.minimal.GatePassInfoProjection;
import com.sowermate.workflow.domain.entities.minimal.GlassInfoProjection;
import com.sowermate.workflow.domain.projection.GatePassProjection;
import com.sowermate.workflow.domain.projection.PiInfoProjectionForReport;
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
    GatePassEntity findByGatePassUuidAndTenantUuid(String gatePassUuid, String tenantUuid, String companyUuid);

    @Query("SELECT gp FROM GatePassEntity gp " +
            "JOIN gp.tenantEntity t " +
            "JOIN gp.companyEntity c " +
            "WHERE t.uuid = :tenantUuid " +
            "AND c.uuid = :companyUuid")
    List<GatePassEntity> findByTenantUuidAndCompanyUuid(String tenantUuid, String companyUuid);

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
            "WHERE " +
            //"c.uuid = :companyUuid " +
            "pi.uuid = :proformaInvoiceUuid " +
            "GROUP BY pi.uuid ")
    GatePassInfoProjection findGatePassInfoByProformaInvoiceUuid(/*String companyUuid,*/ String proformaInvoiceUuid);

    @Query("SELECT " +
            "p.companyIdBill.tenantName as partyName, " +
            "g.uuid as gatePassUuid, " +
            "g.gatePassNo as gatePassNo , " +
            "sum(gd.gatePassQty) as quantity " +
            "FROM ProFormaInvoiceEntity p " +
            "JOIN p.firm c " +
            "JOIN p.gatePassEntities g " +
            "JOIN g.gatePassDetailsEntities gd " +
            "WHERE " +
            //"c.uuid = :companyUuid " +
            "p.uuid = :proformaInvoiceUuid " +
            "GROUP BY g.uuid ")
    List<GatePassDetailsInfoProjection> findGatePassDetailsInfoByProformaInvoiceUuid(/*String companyUuid,*/ String proformaInvoiceUuid);

    @Query("SELECT " +
            "gs.name as glassSpecification , " +
            "gt.name as thickness, " +
            "sum(pii.quantity) as totalItemQty, " +
            "sum(gd.gatePassQty) as gatePassItemQty " +
            "FROM GatePassEntity g " +
            "JOIN g.gatePassDetailsEntities gd " +
            "JOIN gd.proFormaInvoiceItemEntity pii " +
            "JOIN pii.glassSpecificationEntity gs " +
            "JOIN pii.glassThicknessEntity gt " +
            "Where g.uuid = :gatePassUuid " +
            "GROUP BY gs.name, gt.name")
    List<GlassInfoProjection> findGlassItemsInfoByGatePassUuid(String gatePassUuid);


    @Query("SELECT " +
            "p.piNumber as piNo, " +
            "p.invoiceDate as piDate, " +
            "f.tenantName as partyName, " +
            "cb.tenantName as partyBillToName, " +
            "cb.uuid as partyBillToUuid " +
            "FROM GatePassEntity g " +
            "JOIN g.proFormaInvoiceEntity p " +
            "JOIN p.firm f " +
            "JOIN p.companyIdBill cb " +
            "Where g.uuid = :gatePassUuid ")
    PiInfoProjectionForReport findPiItemsInfoByGatePassUuid(String gatePassUuid);

    @Query("SELECT " +
            "g.uuid as uuid, " +
            "t.uuid as tenantUuid, " +
            "c.uuid as companyUuid, " +
            "pi.firm.uuid  as firmUuid, " +
            "g.gatePassNo as gatePassNo, " +
            "g.createdDateTime as dateTime, " +
            "g.createdBy AS createdBy, " +
            "pi.piNumber as piNumber, " +
            "pi.companyIdBill.tenantName as partyName, " +
            "pi.firm.tenantName as firmName, " +
            "CONCAT(g.vehicleNo, '/', g.driverName, '/', g.driverContactNo) as vehicleDetails, " +
            "SUM(gd.gatePassQty) as quantity " +
            "FROM GatePassEntity g " +
            "JOIN g.gatePassDetailsEntities gd " +
            "JOIN g.tenantEntity t " +
            "JOIN g.companyEntity c " +
            "JOIN g.proFormaInvoiceEntity pi " +
            "WHERE t.uuid = :tenantUuid " +
            "AND g.createdDateTime BETWEEN :fromDate AND :toDate " +
            "AND (:companyUuid IS NULL OR pi.companyIdBill.uuid = :companyUuid) " +
            "AND (:firmUuid IS NULL OR pi.firm.uuid = :firmUuid) " + //TODO: temp fix to refer firm froPI but it should be from gate pass        "GROUP BY g.uuid")List<GatePassProjection> findGatePassDetailsWithProformaDetails(        @Param("tenantUuid") String tenantUuid,        @Param("companyUuid") String companyUuid,        @Param("firmUuid") String firmUuid,        @Param("fromDate") LocalDateTime fromDate,        @Param("toDate") LocalDateTime toDate);
            "GROUP BY g.uuid")
    List<GatePassProjection> findGatePassDetailsWithProformaDetails(
            @Param("tenantUuid") String tenantUuid,
            @Param("companyUuid") String companyUuid,
            @Param("firmUuid") String firmUuid,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);
}
