package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.JbCreationEntity;
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

}
