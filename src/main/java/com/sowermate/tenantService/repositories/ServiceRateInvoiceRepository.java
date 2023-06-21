package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ServiceRateInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServiceRateInvoiceRepository extends JpaRepository<ServiceRateInvoiceEntity, String> {

    @Query("SELECT s FROM ServiceRateInvoiceEntity s WHERE s.serviceRateInvoiceUuid = :serviceRateInvoiceUuid")
    public ServiceRateInvoiceEntity findByServiceRateInvoiceUuid(@Param("serviceRateInvoiceUuid") String serviceRateInvoiceUuid);

    public ServiceRateInvoiceEntity deleteByServiceRateInvoiceUuid(@Param("serviceRateInvoiceUuid")String serviceRateInvoiceUuid);

    public List<ServiceRateInvoiceEntity> findAll();
    public List<ServiceRateInvoiceEntity> findByServiceRateInvoiceId(int serviceRateInvoiceId);
}
