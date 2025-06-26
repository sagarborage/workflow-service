package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceIndividualsOrdersProjection;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceOrdersProjection;
import com.sowermate.tenantService.entities.minimal.ProformaInvoiceProjection;
import com.sowermate.tenantService.entities.minimal.ProformaInvoiceWithWorkOrderProjection;
import com.sowermate.tenantService.enums.ProformaInvoiceStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Query("SELECT p FROM ProFormaInvoiceEntity p " +
            "JOIN p.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND p.uuid = :proFormaInvoiceUuid AND p.status = :currentStatus")
    ProFormaInvoiceEntity findByTenantUuidPIUuidAndPICurrentStatus(@Param("tenantUuid") String tenantUuid, @Param("proFormaInvoiceUuid") String proFormaInvoiceUuid, @Param("currentStatus") ProformaInvoiceStatusEnum currentStatus);


    Optional<ProFormaInvoiceEntity> findById(String proFormaInvoiceId);

    //PROD issue fix: duplicate PI/Numbers where getting created
    ProFormaInvoiceEntity findFirstByTenantEntityIdOrderByCreatedDateTimeDesc(long id);

    ProFormaInvoiceEntity findFirstByTenantEntityIdOrderByIdDesc(long id);
    //ProFormaInvoiceEntity findByUuid(String uuid);
    //List<ProFormaInvoiceEntity> findAllByTenantEntity_Id(long tenantId);

    ProFormaInvoiceEntity findByUuid(String uuid);

    //TODO: Remove this method later on if below new method works fine
/*    @Query("SELECT pfie.uuid as uuid, " +
            "c.companyName as partyName, " +
            "pfie.piNumber as piNumber, " +
            "ct.uuid as confirmThroughUuid, " +
            "woe.uuid as workOrderUuid, " +
            "woe.id as workOrderNumber, " +
            "pfie.payableAmount as payableAmount, " +
            "pfie.invoiceDate as invoiceDate, " +
            "pfie.status as status FROM ProFormaInvoiceEntity pfie " +
            "JOIN pfie.companyIdBill c " +
            "LEFT JOIN pfie.confirmThroughEntity ct " +
            "LEFT JOIN pfie.workOrderEntity woe " +
            "where pfie.tenantEntity.uuid = :tenantUuid and pfie.invoiceDate between :startDate and :endDate ORDER BY pfie.invoiceDate DESC")
    List<ProFormaInvoiceMinimal> findAllByTenantUuid(@Param("tenantUuid") String tenantUuid, LocalDateTime startDate, LocalDateTime endDate);*/

    @Query("SELECT pfie FROM ProFormaInvoiceEntity pfie " +
            "where pfie.tenantEntity.uuid = :tenantUuid and " +
            //"pfie.firm.uuid = :companyUuid and " + //TODO: temp fix to allow all company, but it should be tenant specific companies
            "pfie.invoiceDate between :startDate and :endDate ORDER BY pfie.lastUpdatedDateTime DESC")
    List<ProFormaInvoiceEntity> findAllByTenantUuid(@Param("tenantUuid") String tenantUuid, /*String companyUuid, */LocalDateTime startDate, LocalDateTime endDate);

    @Modifying
    @Query("DELETE FROM ProFormaInvoiceEntity g WHERE g.uuid = :proFormaInvoiceUuid")
    int deleteByUuid(@Param("proFormaInvoiceUuid") String proFormaInvoiceUuid);

    @Query("Select pi.uuid as proformaInvoiceUuid, " +
            "pi.piNumber as piNumber, " +
            "co.companyName as partyBillTo, " +
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
            "co.companyName as partyBillTo, " +
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
            "co.companyName as partyBillTo, " +
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
            "co.companyName as partyBillTo, " +
            "wo.id as workOrderNo " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.companyIdBill co " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "where pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid) and " +
            "pii.toughenBucket >= 1 Group By pi.uuid")
    List<ProFormaInvoiceOrdersProjection> findAllPiOrdersDetailsOfToughen(String tenantUuid);

    @Query("Select pi.uuid as proformaInvoiceUuid," +
            "pi.tenantEntity.uuid as tenantUuid, " +
            "pii.uuid as proformaInvoiceItemUuid, " +
            "pi.piNumber as piNumber, " +
            "wo.id as workOrderNo, " +
            "wo.uuid as workOrderUuid, " +
            "f.uuid as companyUuid, " +
            "gt.glassName as glassType, " +
            "pii.actualWidth as actualWidth, " +
            "pii.actualHeight as actualHeight, " +
            "gth.name as glassThickness, " +
            "pii.quantity as quantity, " +
            "pii.optimizeBucket as bucketQuantity, " +
            "pii.optimizeCompleted as completedQuantity, " +
            "pii.status as status, " +
            "pii.statusDetails as statusDetails " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.firm f " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "join pii.glassTypeEntity gt " +
            "join pii.glassThicknessEntity gth " +
            "where wo.id = :workOrderNumber and " +
            //"pii.status = 'IN_PROGRESS' and " + TODO: to hold and un-hole we need to comment-out this condition..need discussion on the scenario and solution
            "pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid)")
    List<ProFormaInvoiceIndividualsOrdersProjection> findAllPiOrdersDetailsOfOptimizeIndividual(String tenantUuid, Integer workOrderNumber);

    @Query("Select pi.uuid as proformaInvoiceUuid," +
            "pi.tenantEntity.uuid as tenantUuid, " +
            "pii.uuid as proformaInvoiceItemUuid, " +
            "pi.piNumber as piNumber, " +
            "wo.id as workOrderNo, " +
            "wo.uuid as workOrderUuid, " +
            "f.uuid as companyUuid, " +
            "gt.glassName as glassType, " +
            "pii.actualWidth as actualWidth, " +
            "pii.actualHeight as actualHeight, " +
            "gth.name as glassThickness, " +
            "pii.quantity as quantity, " +
            "pii.cuttingBucket as bucketQuantity, " +
            "pii.cuttingCompleted as completedQuantity, " +
            "pii.status as status, " +
            "pii.statusDetails as statusDetails " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.firm f " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "join pii.glassTypeEntity gt " +
            "join pii.glassThicknessEntity gth " +
            "where wo.id = :workOrderNumber and " +
            //"pii.status = 'IN_PROGRESS' and " +
            "pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid)")
    List<ProFormaInvoiceIndividualsOrdersProjection> findAllPiOrdersDetailsOfCuttingIndividual(String tenantUuid, Integer workOrderNumber);

    @Query("Select pi.uuid as proformaInvoiceUuid," +
            "pi.tenantEntity.uuid as tenantUuid, " +
            "pii.uuid as proformaInvoiceItemUuid, " +
            "pi.piNumber as piNumber, " +
            "wo.id as workOrderNo, " +
            "wo.uuid as workOrderUuid, " +
            "f.uuid as companyUuid, " +
            "gt.glassName as glassType, " +
            "pii.actualWidth as actualWidth, " +
            "pii.actualHeight as actualHeight, " +
            "gth.name as glassThickness, " +
            "pii.quantity as quantity, " +
            "pii.dispatchBucket as bucketQuantity, " +
            "pii.dispatchCompleted as completedQuantity, " +
            "pii.status as status, " +
            "pii.statusDetails as statusDetails " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.firm f " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "join pii.glassTypeEntity gt " +
            "join pii.glassThicknessEntity gth " +
            "where wo.id = :workOrderNumber and " +
            //"pii.status = 'IN_PROGRESS' and " +
            "pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid)")
    List<ProFormaInvoiceIndividualsOrdersProjection> findAllPiOrdersDetailsOfDispatchIndividual(String tenantUuid, Integer workOrderNumber);

    @Query("Select pi.uuid as proformaInvoiceUuid," +
            "pi.tenantEntity.uuid as tenantUuid, " +
            "pii.uuid as proformaInvoiceItemUuid, " +
            "pi.piNumber as piNumber, " +
            "wo.id as workOrderNo, " +
            "wo.uuid as workOrderUuid, " +
            "f.uuid as companyUuid, " +
            "gt.glassName as glassType, " +
            "gs.name as glassSpecification, " +
            "pii.actualWidth as actualWidth, " +
            "pii.actualHeight as actualHeight, " +
            "gth.name as glassThickness, " +
            "pii.quantity as quantity, " +
            "pii.toughenBucket as bucketQuantity, " +
            "pii.toughenCompleted as completedQuantity, " +
            "pii.status as status, " +
            "pii.statusDetails as statusDetails " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.firm f " +
            "join pi.proFormaInvoiceItemEntities pii " +
            "join pii.glassTypeEntity gt " +
            "join pii.glassSpecificationEntity gs " +
            "join pii.glassThicknessEntity gth " +
            "where wo.id = :workOrderNumber and " +
            //"pii.status = 'IN_PROGRESS' and " +
            "pi.tenantEntity = (select t from TenantEntity  t where t.uuid = :tenantUuid)")
    List<ProFormaInvoiceIndividualsOrdersProjection> findAllPiOrdersDetailsOfToughenIndividual(String tenantUuid, Integer workOrderNumber);

    @Query("select " +
            "pi.uuid as uuid, " +
            "pi.tenantEntity.uuid as tenantUuid, " +
            "f.companyName as firm, " +
            "f.uuid as companyUuid, " +
            "pi.piNumber as piNumber, " +
            "pi.companyIdBill.companyName as partyName, " +
            "pi.invoiceDate as invoiceDateTime, " +
            "pi.payableAmount as amount, " +
            "pi.status as status, " +
            "pi.createdBy as user " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.firm f " +
            "join pi.companyIdBill cb " +
            "where (:partyUuid IS NULL OR cb.uuid = :partyUuid) " +
            "and (:companyUuid IS NULL OR f.uuid = :companyUuid) " +
            "and (:status IS NULL OR pi.status = :status) " +
            "and pi.invoiceDate between :fromDate and :toDate " +
            "and pi.tenantEntity.uuid = :tenantUuid")
    List<ProformaInvoiceProjection> findAllPiOrdersDetails(
            @Param("tenantUuid") String tenantUuid,
            @Param("companyUuid") String companyUuid,
            @Param("partyUuid") String partyUuid,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("status") ProformaInvoiceStatusEnum status);

    @Query("select " +
            "pi.uuid as uuid, " +
            "pi.tenantEntity.uuid as tenantUuid, " +
            "pi.piNumber as piNumber, " +
            "pi.companyIdBill.companyName as partyName, " +
            "pi.firm.companyName as firmName, " +
            "pi.createdDateTime as PIDateTime, " +
            "pi.payableAmount as amount, " +
            "pi.createdBy as user, " +
            "pt.piTypeName as piType, " +
            "wo.uuid as workOrderUuid, " +
            "wo.createdDateTime as workOrderDateTime, " +
            "case when pt.piTypeName = 'SQFT' then SUM(pit.unitValue) else null end as SQFT, " +
            "case when pt.piTypeName = 'MM' then SUM(pit.unitValue) else null end as SQMTR " +
            "from ProFormaInvoiceEntity pi " +
            "join pi.workOrderEntity wo " +
            "join pi.piTypeEntity pt " +
            "join pi.proFormaInvoiceItemEntities pit " +
            "where (:workOrderUuid IS NULL OR wo.uuid = :workOrderUuid) " +
            "and pi.createdDateTime between :fromDate and :toDate " +
            "and pi.tenantEntity.uuid = :tenantUuid " +
            "GROUP BY pi.uuid")
    List<ProformaInvoiceWithWorkOrderProjection> findAllPiOrdersDetailsWithWorkOrderDetails(
            @Param("tenantUuid") String tenantUuid,
            @Param("workOrderUuid") String workOrderUuid,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);

}
