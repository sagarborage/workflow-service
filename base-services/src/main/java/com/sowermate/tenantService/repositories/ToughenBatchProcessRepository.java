package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ToughenBatchProcessEntity;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToughenBatchProcessRepository extends JpaRepository<ToughenBatchProcessEntity, String> {

    //@Query("select * from ")
    //ToughenBatchProcessEntity findByTenantAndFirmAndStatus(ToughenBatchProcessStatusEnum status);
    List<ToughenBatchProcessEntity> findAllByCompanyEntityUuidAndStatus(String companyUuid, ToughenBatchProcessStatusEnum status);
    Optional<ToughenBatchProcessEntity> findFirstByCompanyEntityUuidOrderByCreatedDateTimeDesc(String companyUuid);
}
