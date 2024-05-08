package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import com.sowermate.tenantService.entities.ToughenBatchProcessDetailsEntity;
import com.sowermate.tenantService.entities.ToughenBatchProcessEntity;
import com.sowermate.tenantService.entities.minimal.ToughenBatchProcessProjection;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
            "JOIN p.companyIdBill c " +
            "WHERE c.uuid =:companyUuid  " +
            "AND tb.batchNo = :batchNo " +
            "AND tb.status = :status ")
    Optional<List<ToughenBatchProcessEntity>> findByBatchNoAndCompanyUuidAndStatus(Integer batchNo,String companyUuid,ToughenBatchProcessStatusEnum status);

    @Query("SELECT " +
            "tbd.uuid as batchItemUuid, " +
            "tb.uuid as batchUuid, " +
            "tb.batchNo as batchNo, " +
            "pi.uuid as proformaInvoiceItemUuid, " +
            "Date(tb.createdDateTime) as batchDate, " +
            "p.piNumber as piNo, " +
            "p.uuid as proformaInvoiceUuid , " +
            "c.companyName as billToPartyName, " +
            "c.uuid as billToPartyUuid, " +
            "th.name as thickness, "+
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
            "JOIN p.companyIdBill c " +
            "WHERE c.uuid =:companyUuid AND " +
            "tb.status = :status ")
    List<ToughenBatchProcessProjection> findByCompanyUuidAndStatus(String companyUuid, ToughenBatchProcessStatusEnum status);

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
            "WHERE tbd.uuid = :uuid " +
            "AND c.uuid = :companyUuid ")
    ToughenBatchProcessDetailsEntity findToughenBatchProcessDetailsEntityByUuidAndCompanyUuid(String uuid,String companyUuid);


}
