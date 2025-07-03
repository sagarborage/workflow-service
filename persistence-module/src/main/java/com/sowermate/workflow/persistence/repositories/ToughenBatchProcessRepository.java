package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.ProFormaInvoiceItemEntity;
import com.sowermate.workflow.domain.entities.ToughenBatchProcessDetailsEntity;
import com.sowermate.workflow.domain.entities.ToughenBatchProcessEntity;
import com.sowermate.workflow.domain.entities.minimal.CompletedGlassesProjection;
import com.sowermate.workflow.domain.entities.minimal.ToughenBatchProcessProjection;
import com.sowermate.workflow.domain.entities.minimal.ToughenReportProjection;
import com.sowermate.workflow.domain.entities.minimal.ViewToughenBatchProcessDetailsProjection;
import com.sowermate.workflow.domain.enums.ToughenBatchProcessStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ToughenBatchProcessRepository extends JpaRepository<ToughenBatchProcessEntity, String> {

    List<ToughenBatchProcessEntity> findAllByCompanyEntityUuidAndStatus(String companyUuid, ToughenBatchProcessStatusEnum status);

    Optional<ToughenBatchProcessEntity> findFirstByCompanyEntityUuidOrderByCreatedDateTimeDesc(String companyUuid);

    Optional<List<ToughenBatchProcessEntity>> findByStatusOrderByCreatedDateTimeDesc(ToughenBatchProcessStatusEnum status);

    Optional<List<ToughenBatchProcessEntity>> findByOrderByCreatedDateTimeDesc();

    Optional<List<ToughenBatchProcessEntity>> findByStatusNotOrderByCreatedDateTimeDesc(ToughenBatchProcessStatusEnum status);

    @Query("SELECT tb " +
            "FROM ToughenBatchProcessEntity tb " +
            "JOIN tb.companyEntity firm " +
            "JOIN tb.toughenBatchProcessDetailsEntities tbd " +
            "JOIN tbd.proFormaInvoiceItemEntity pi " +
            "JOIN pi.proFormaInvoiceEntity p " +
            "JOIN p.firm f " +
            "WHERE " +
            //"f.uuid =:firmUuid  " + //TODO: temp fix to allow all company, but it should be tenant specific companies
            "tb.batchNo = :batchNo " +
            "AND tb.status = :status UNION " +
            "SELECT tb " +
            "FROM ToughenBatchProcessEntity tb " +
            "JOIN tb.companyEntity firm " +
            "JOIN tb.jbCreationEntities jb " +
            "WHERE " +
           //"firm.uuid =:firmUuid " + //TODO: temp fix to allow all company, but it should be tenant specific companies
            "tb.batchNo = :batchNo " +
            "AND tb.status = :status "
    )
    Optional<List<ToughenBatchProcessEntity>> findByBatchNoAndCompanyUuidAndStatus(Integer batchNo, ToughenBatchProcessStatusEnum status);

    @Query("SELECT " +
            "tbd.uuid as batchItemUuid, " +
            "tb.uuid as batchUuid, " +
            "tb.batchNo as batchNo, " +
            //" 0 as workOrderNo, " +
            "wo.id  as workOrderNo, " +
            "pi.uuid as proformaInvoiceItemUuid, " +
            "'WORK_ORDER' as itemType, " +
            "Date(tb.createdDateTime) as batchDate, " +
            "p.piNumber as piNo, " +
            "p.uuid as proformaInvoiceUuid , " +
            "p.companyIdBill.tenantName as billToPartyName, " +
            "p.companyIdBill.uuid as billToPartyUuid, " +
            "th.name as thickness, " +
            "pi.actualWidth as actualWidth, " +
            "pi.chargeableWidth as chargeableWidth, " +
            "pi.actualHeight as actualHeight, " +
            "pi.chargeableHeight as chargeableHeight, " +
            "tbd.status as itemStatus " +
            "FROM ToughenBatchProcessEntity tb " +
            "JOIN tb.companyEntity firm " +
            "JOIN tb.toughenBatchProcessDetailsEntities tbd " +
            "JOIN tbd.proFormaInvoiceItemEntity pi " +
            "JOIN pi.glassThicknessEntity th " +
            "JOIN pi.proFormaInvoiceEntity p " +
            "JOIN p.workOrderEntity wo " +
            "JOIN p.firm f " +
            "WHERE " +
            //"f.uuid =:firmUuid AND " + //TODO: temp fix to allow all company, but it should be tenant specific companies
            "tb.status = :status UNION " +
            "SELECT " +
            "jb.uuid as batchItemUuid, " +
            "tbpe.uuid as batchUuid, " +
            "tbpe.batchNo as batchNo, " +
            "0L as workOrderNo, " +
            "'N/A' as proformaInvoiceItemUuid, " +
            "'JB' as itemType, " +
            "Date(tbpe.createdDateTime) as batchDate, " +
            "'JB/' as piNo, " +
            "'N/A' as proformaInvoiceUuid, " +
            "jb.partyName as billToPartyName, " +
            "'N/A' as billToPartyUuid, " +
            "jb.glassThicknessEntity.name as thickness, " +
            "jb.widthMm as actualWidth, " +
            "jb.widthMm as chargeableWidth, " +
            "jb.heightMm as actualHeight, " +
            "jb.heightMm as chargeableHeight, " +
            "jb.status as itemStatus " +
            "FROM JbCreationEntity jb " +
            "JOIN jb.toughenBatchProcessEntity tbpe " +
            "where tbpe.status = :status"
    )
    List<ToughenBatchProcessProjection> findByCompanyUuidAndStatus(ToughenBatchProcessStatusEnum status);

    @Query("SELECT " +
            "gt.name AS thickness, " +
            "pii.chargeableWidth AS widthMm, " +
            "pii.chargeableHeight AS heightMm, " +
            "pii.quantity AS quantity " +
            "FROM ToughenBatchProcessEntity tbp " +
            "JOIN tbp.toughenBatchProcessDetailsEntities tbpd " +
            "JOIN tbpd.proFormaInvoiceItemEntity pii " +
            "JOIN pii.glassThicknessEntity gt " +
            "WHERE DATE(tbp.createdDateTime) = :date " +
            "AND tbp.status = 'COMPLETED' ")
    List<ToughenReportProjection> findToughenReportByDate(@Param("date") LocalDate date);

    @Query("SELECT g.name FROM GlassThicknessEntity g")
    List<String> findToughenThickness();

    @Query("SELECT " +
            "gt.name AS thickness, " +
            "jb.widthMm AS widthMm, " +
            "jb.heightMm AS heightMm, " +
            "jb.quantity AS quantity " +
            "FROM ToughenBatchProcessEntity tbp " +
            "JOIN tbp.jbCreationEntities jb " +
            "JOIN jb.glassThicknessEntity gt " +
            "WHERE DATE(tbp.createdDateTime) = :date ")
    List<ToughenReportProjection> findToughenReportJbCreationByDate(@Param("date") LocalDate date);

    @Query("SELECT " +
            "tbd.uuid as batchItemUuid, " +
            "tb.batchNo as batchNo, " +
            "Date(tb.createdDateTime) as batchDate, " +
            "p.piNumber as piNo, " +
            "p.companyIdBill.tenantName as billToPartyName, " +
            "th.name as thickness, " +
            "pi.actualWidth as actualWidth, " +
            "pi.chargeableWidth as chargeableWidth, " +
            "pi.actualHeight as actualHeight, " +
            "pi.chargeableHeight as chargeableHeight " +
            "FROM ToughenBatchProcessEntity tb " +
            "JOIN tb.tenantEntity t " +
            "JOIN tb.toughenBatchProcessDetailsEntities tbd " +
            "JOIN tbd.proFormaInvoiceItemEntity pi " +
            "JOIN pi.glassThicknessEntity th " +
            "JOIN pi.proFormaInvoiceEntity p " +
            "JOIN p.firm f " +
            "WHERE " +
            //"f.uuid =:firmUuid AND " + //TODO: temp fix to allow all company, but it should be tenant specific companies
            "t.uuid =:tenantUuid AND " +
            "Date(tb.createdDateTime) =:batchProcessingDate " +
            "AND tb.status = 'COMPLETED'" +
            "UNION ALL " +
            "SELECT " +
            "tb.uuid as batchUuid," +
            "tb.batchNo as batchNo, " +
            "Date(tb.createdDateTime) as batchDate, " +
            "CONCAT('JB/', jb.id) AS piNo, " +
            "jb.partyName as billToPartyName, " +
            "jb.glassThicknessEntity.name as thickness, " +
            "jb.widthMm as actualWidth, " +
            "jb.widthMm as chargeableWidth, " +
            "jb.heightMm as actualHeight, " +
            "jb.heightMm as chargeableHeight " +
            "FROM JbCreationEntity jb " +
            "JOIN jb.toughenBatchProcessEntity tb " +
            // "JOIN tb.tenantEntity t " +
            //"JOIN tb.toughenBatchProcessDetailsEntities tbd " +
            //"JOIN tbd.proFormaInvoiceItemEntity pi " +
            //"JOIN pi.glassThicknessEntity th " +
            //"JOIN pi.proFormaInvoiceEntity p " +
            //"JOIN p.firm f " +
            "WHERE " +
            //"t.uuid =:tenantUuid AND " +
            "Date(tb.createdDateTime) =:batchProcessingDate " +
            "ORDER BY tb.batchNo desc"
    )
//TODO:: this has to be handled with multiple company type (JB0
    List<ViewToughenBatchProcessDetailsProjection> findByViewToughBatchProcess(String tenantUuid, LocalDate batchProcessingDate);


    @Query("SELECT " +
            "pi " +
            "FROM ToughenBatchProcessEntity tb " +
            "JOIN tb.toughenBatchProcessDetailsEntities tbd " +
            "JOIN tbd.proFormaInvoiceItemEntity pi " +
            "WHERE tb.id = :batchId ")
    Optional<List<ProFormaInvoiceItemEntity>> findByBatchNo(Long batchId);

    @Query("SELECT pi " +
            "From ToughenBatchProcessDetailsEntity tbd " +
            "JOIN tbd.proFormaInvoiceItemEntity pi " +
            "WHERE tbd.uuid = :uuid ")
    ProFormaInvoiceItemEntity getPIItemToBeCancelled(@Param("uuid") String uuid);

    @Query("SELECT tbd " +
            "FROM ToughenBatchProcessEntity tb " +
            "JOIN tb.companyEntity c " +
            "JOIN tb.toughenBatchProcessDetailsEntities tbd " +
            "WHERE tbd.uuid = :uuid "
            //"AND c.uuid = :companyUuid " //TODO: sagar
            )
    ToughenBatchProcessDetailsEntity findToughenBatchProcessDetailsEntityByUuidAndCompanyUuid(String uuid/*, String companyUuid*/);

    ToughenBatchProcessEntity findByUuid(@Param("toughenBatchProcessUuid") String toughenBatchProcessUuid);


    @Query("SELECT " +
            "gt.id as id, " +
            "gt.name as thickness, " +
            "CASE WHEN pii.dispatchCompleted is NULL THEN 0 ELSE pii.dispatchCompleted END as dispatchCompleted, " +
            "CASE WHEN pii.quantity is NULL THEN 0 ELSE pii.quantity END as totalQuantity, " +
            "CASE WHEN pii.unitValue is NULL THEN 0 ELSE pii.unitValue END as unitValue " +
            "FROM GlassThicknessEntity gt " +
            "JOIN gt.tenantEntity t " +
            "LEFT JOIN gt.proFormaInvoiceItemEntities pii " +
            "LEFT JOIN pii.toughenBatchProcessDetailsEntity tbd " +
            "LEFT JOIN tbd.toughenBatchProcessEntity tb " +
            "LEFT JOIN tb.companyEntity c " +
            "WHERE t.uuid = :tenantUuid " +
            "AND(c.uuid is NULL OR c.uuid = :companyUuid) " +
            "AND(Date(tb.createdDateTime) is NULL OR Date(tb.createdDateTime) =:batchProcessingDate) ")
    List<CompletedGlassesProjection> findByThickness(String tenantUuid, String companyUuid, LocalDate batchProcessingDate);
}
