package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ToughenBatchProcessDetailsEntity;
import com.sowermate.tenantService.entities.minimal.StickerReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToughenBatchProcessDetailsRepository extends JpaRepository<ToughenBatchProcessDetailsEntity, String> {

    ToughenBatchProcessDetailsEntity findByUuid(@Param("toughenBatchProcessDetailsUuid") String toughenBatchProcessDetailsUuid);

    @Query("Select " +
            "pi.piNumber as piNo, " +
            "th.name as thickness, " +
            "c.companyName as partyName, " +
            "CONCAT(ROUND(pii.actualHeight,0),'*',ROUND(pii.actualWidth,0)) as size, " +
            "CONCAT(tbd.stickerNumber,'/',pii.quantity) as stickerNumber " +
            "From ToughenBatchProcessDetailsEntity tbd " +
            "JOIN tbd.proFormaInvoiceItemEntity pii " +
            "JOIN pii.glassThicknessEntity th "+
            "JOIN pii.proFormaInvoiceEntity pi " +
            "JOIN pi.firm as f " +
            "JOIN pi.companyIdBill as c " +
            "JOIN pi.tenantEntity as t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND f.uuid = :CompanyUuid " +
            "AND tbd.uuid = :batchItemUuid " )
    StickerReportProjection findByStickerData(String tenantUuid, String CompanyUuid, String batchItemUuid);
}

