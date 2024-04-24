package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ToughenBatchProcessEntity;
import com.sowermate.tenantService.enums.ProformaInvoiceStatusEnum;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
