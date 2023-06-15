package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ServiceRateInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServiceRateInvoiceRepository extends JpaRepository<ServiceRateInvoiceEntity, String> {

    @Query("SELECT p FROM ServiceRateInvoiceEntity p WHERE p.uuid = :uuid")
    public List<ServiceRateInvoiceEntity> findByUuid(@Param("uuid") String uuid);

    public List<ServiceRateInvoiceEntity> deleteByUuid(@Param("uuid")String uuid);

    public List<ServiceRateInvoiceEntity> findAll();
    public List<ServiceRateInvoiceEntity> findByServiceRateInvoiceId(int serviceRateInvoiceId);
}
