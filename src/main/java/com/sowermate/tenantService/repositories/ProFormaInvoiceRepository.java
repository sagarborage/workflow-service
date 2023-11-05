package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceIndividualsOrdersProjection;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceMinimal;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceOrdersProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProFormaInvoiceRepository extends JpaRepository<ProFormaInvoiceEntity, String> {

    // @Query("SELECT p FROM ProFormaInvoiceEntity p WHERE p.proFormaInvoiceUuid = :proFormaInvoiceUuid")
    // public ProFormaInvoiceEntity findByProFormaInvoiceUuid(@Param("proFormaInvoiceUuid") String proFormaInvoiceUuid);

    //  public ProFormaInvoiceEntity deleteByProFormaInvoiceUuid(@Param("proFormaInvoiceUuid")String proFormaInvoiceUuid);

    //   public List<ProFormaInvoiceEntity> findAll();
    // public List<ProFormaInvoiceEntity> findByProFormaInvoiceId(int proFormaInvoiceId);


    @Query("SELECT p FROM ProFormaInvoiceEntity p " +
            "JOIN p.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND p.uuid = :proFormaInvoiceUuid")
    ProFormaInvoiceEntity findByTenantEntity_UuidAndproFormaInvoiceUuid(@Param("tenantUuid") String tenantUuid, @Param("proFormaInvoiceUuid") String proFormaInvoiceUuid);

    Optional<ProFormaInvoiceEntity> findById(String proFormaInvoiceId);

    ProFormaInvoiceEntity findFirstByTenantEntityIdOrderByCreatedDateTimeDesc(long id);

    ProFormaInvoiceEntity findByUuid(String uuid);
    //List<ProFormaInvoiceEntity> findAllByTenantEntity_Id(long tenantId);

    @Query("SELECT pfie.uuid as uuid, " +
            "c.companyName as partyName, " +
            "pfie.piNumber as piNumber, " +
            "pfie.payableAmount as payableAmount, " +
            "pfie.invoiceDate as invoiceDate, " +
            "pfie.status as status FROM ProFormaInvoiceEntity pfie " +
            " JOIN pfie.companyIdBill c where  " +
            "pfie.tenantEntity.uuid = :tenantUuid and pfie.invoiceDate between :startDate and :endDate ORDER BY pfie.invoiceDate DESC")
    List<ProFormaInvoiceMinimal> findAllByTenantUuid(@Param("tenantUuid") String tenantUuid, LocalDateTime startDate, LocalDateTime endDate);

    @Modifying
    @Query("DELETE FROM ProFormaInvoiceEntity g WHERE g.uuid = :proFormaInvoiceUuid")
    int deleteByUuid(@Param("proFormaInvoiceUuid") String proFormaInvoiceUuid);

    @Query("Select pi.uuid as proformaInvoiceUuid, " +
            "pi.piNumber as piNumber, " +
            "co.id as partyBillTo, " +
            "wo.id as workOrderNo " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.companyIdBill co " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "where pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid) and " +
            "pii.optimizeBucket >= 1 Group By pi.uuid")
    List<ProFormaInvoiceOrdersProjection> findAllPiOrdersDetailsOfOptimize(String tenantUuid);

    @Query("Select pi.uuid as proformaInvoiceUuid, " +
            "pi.piNumber as piNumber, " +
            "co.id as partyBillTo, " +
            "wo.id as workOrderNo " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.companyIdBill co " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "where pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid) and " +
            "pii.cuttingBucket >= 1 Group By pi.uuid")
    List<ProFormaInvoiceOrdersProjection> findAllPiOrdersDetailsOfCutting(String tenantUuid);

    @Query("Select pi.uuid as proformaInvoiceUuid, " +
            "pi.piNumber as piNumber, " +
            "co.id as partyBillTo, " +
            "wo.id as workOrderNo " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.companyIdBill co " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "where pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid) and " +
            "pii.dispatchBucket >= 1 Group By pi.uuid")
    List<ProFormaInvoiceOrdersProjection> findAllPiOrdersDetailsOfDispatch(String tenantUuid);

    @Query("Select pi.uuid as proformaInvoiceUuid, " +
            "pi.piNumber as piNumber, " +
            "co.id as partyBillTo, " +
            "wo.id as workOrderNo " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.companyIdBill co " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "where pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid) and " +
            "pii.toughenBucket >= 1 Group By pi.uuid")
    List<ProFormaInvoiceOrdersProjection> findAllPiOrdersDetailsOfToughen(String tenantUuid);

    @Query("Select pi.piNumber as piNumber, " +
            "wo.id as workOrderNo, " +
            "gt.glassName as glassType, " +
            "pii.actualWidth as actualWidth, " +
            "pii.actualHeight as actualHeight, " +
            "gth.name as glassThickness, " +
            "pii.quantity as quantity, " +
            "pii.optimizeBucket as bucketQuantity, " +
            "pii.optimizeCompleted as completedQuantity " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.companyIdBill co " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "join pii.glassTypeEntity gt " +
            "join pii.glassThicknessEntity gth " +
            "where pi.uuid = :proFormaInvoiceUuid and " +
            "pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid) and " +
            "pii.optimizeBucket >= 1 ")
    List<ProFormaInvoiceIndividualsOrdersProjection> findAllPiOrdersDetailsOfOptimizeIndividual(String tenantUuid, String proFormaInvoiceUuid);

    @Query("Select pi.piNumber as piNumber, " +
            "wo.id as workOrderNo, " +
            "gt.glassName as glassType, " +
            "pii.quantity as quantity, " +
            "pii.cuttingBucket as bucketQuantity, " +
            "pii.cuttingCompleted as completedQuantity " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.companyIdBill co " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "join pii.glassTypeEntity gt " +
            "where pi.uuid = :proFormaInvoiceUuid and " +
            "pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid) and " +
            "pii.cuttingBucket >= 1")
    List<ProFormaInvoiceIndividualsOrdersProjection> findAllPiOrdersDetailsOfCuttingIndividual(String tenantUuid, String proFormaInvoiceUuid);

    @Query("Select pi.piNumber as piNumber, " +
            "wo.id as workOrderNo, " +
            "gt.glassName as glassType, " +
            "pii.quantity as Quantity, " +
            "pii.dispatchBucket as bucketQuantity, " +
            "pii.dispatchCompleted as completedQuantity " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.companyIdBill co " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "join pii.glassTypeEntity gt " +
            "where pi.uuid = :proFormaInvoiceUuid and " +
            "pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid) and " +
            "pii.dispatchBucket >= 1")
    List<ProFormaInvoiceIndividualsOrdersProjection> findAllPiOrdersDetailsOfDispatchIndividual(String tenantUuid, String proFormaInvoiceUuid);

    @Query("Select pi.piNumber as piNumber, " +
            "wo.id as workOrderNo, " +
            "gt.glassName as glassType, " +
            "pii.quantity as Quantity, " +
            "pii.toughenBucket as bucketQuantity, " +
            "pii.toughenCompleted as completedQuantity " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.companyIdBill co " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "join pii.glassTypeEntity gt " +
            "where pi.uuid = :proFormaInvoiceUuid and " +
            "pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid) and " +
            "pii.toughenBucket >= 1")
    List<ProFormaInvoiceIndividualsOrdersProjection> findAllPiOrdersDetailsOfToughenIndividual(String tenantUuid, String proFormaInvoiceUuid);


}
