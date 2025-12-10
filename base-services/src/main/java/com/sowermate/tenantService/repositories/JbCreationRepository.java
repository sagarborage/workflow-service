package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.JbCreationEntity;
import com.sowermate.tenantService.entities.minimal.StickerReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JbCreationRepository extends JpaRepository<JbCreationEntity, Long> {

    @Query("SELECT j FROM JbCreationEntity j " +
            "JOIN j.toughenBatchProcessEntity t " +
            "WHERE t.uuid = :toughenBatchProcessUuid " +
            "AND j.uuid = :jbCreationUuid")
    JbCreationEntity findByToughenBatchProcessEntity_UuidAndJbCreationEntityUuid(String toughenBatchProcessUuid, String jbCreationUuid);

    List<JbCreationEntity> findAllByToughenBatchProcessEntityUuid(String toughenBatchProcessUuid);


    @Transactional
    @Modifying
    @Query("UPDATE JbCreationEntity j SET j.isActive = false WHERE j.toughenBatchProcessEntity.uuid = :toughenBatchProcessUuid and j.uuid = :jbCreationUuid")
    void softDelete(@Param("toughenBatchProcessUuid") String toughenBatchProcessDetailsUuid, @Param("jbCreationUuid") String jbCreationUuid);


    JbCreationEntity getJbCreationEntityByUuid(@Param("jbCreationUuid") String jbCreationUuid);


    @Query("Select " +
            "'-' as piNo, " +
            "th.name as thickness, " +
            "jb.partyName as partyName, " +
            "CONCAT(ROUND(jb.heightMm,0),'*',ROUND(jb.widthMm,0)) as size, " +
            "'-' as stickerNumber " +
            "From JbCreationEntity jb " +
            "JOIN jb.glassThicknessEntity th "+
            "WHERE jb.uuid = :jbUuid ")
    StickerReportProjection findByStickerData(String jbUuid);

}
