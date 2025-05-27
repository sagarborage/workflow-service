package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.ServiceRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ServiceRateRepository extends JpaRepository<ServiceRateEntity, String> {

    //   public ServiceRateEntity findByServiceRateUuid(@Param("serviceRateUuid")String serviceRateUuid);


    @Query("SELECT s FROM ServiceRateEntity s " +
            "JOIN s.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND s.uuid = :serviceRateUuid")
    public ServiceRateEntity findByTenantEntity_UuidAndServiceRateUuid(@Param("tenantUuid") String tenantUuid, @Param("serviceRateUuid") String serviceRateUuid);
    //public List<ServiceRateEntity> findByServiceRateId(int serviceRateId);

    @Transactional
    @Modifying
    @Query("UPDATE ServiceRateEntity s SET s.isActive = false WHERE s.uuid = :serviceRateUuid")
    void softDelete(@Param("serviceRateUuid") String serviceRateUuid);

    public List<ServiceRateEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    ServiceRateEntity findByUuid(String uuid);
}
