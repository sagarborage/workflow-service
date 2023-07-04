package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ServiceRateEntity;
import com.sowermate.tenantService.entities.ServiceRateInvoiceEntity;
import com.sowermate.tenantService.entities.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServiceRateInvoiceRepository extends JpaRepository<ServiceRateInvoiceEntity, String> {

//    @Query("SELECT s FROM ServiceRateInvoiceEntity s WHERE s.serviceRateInvoiceUuid = :serviceRateInvoiceUuid")
 //   public ServiceRateInvoiceEntity findByServiceRateInvoiceUuid(@Param("serviceRateInvoiceUuid") String serviceRateInvoiceUuid);



   // public List<ServiceRateInvoiceEntity> findAll();
    // public List<ServiceRateInvoiceEntity> findByServiceRateInvoiceId(int serviceRateInvoiceId);

    @Query("SELECT s FROM ServiceRateInvoiceEntity s " +
            "JOIN s.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND s.serviceRateInvoiceUuid = :serviceRateInvoiceUuid")
    public ServiceRateInvoiceEntity findByTenantEntity_UuidAndServiceRateInvoiceUuid(@Param("tenantUuid") String tenantUuid, @Param("serviceRateInvoiceUuid") String serviceRateInvoiceUuid);


    public int deleteByServiceRateInvoiceUuid(@Param("serviceRateInvoiceUuid")String serviceRateInvoiceUuid);

    public List<ServiceRateInvoiceEntity> findAllByTenantEntity_Uuid(String tenantUuid);
}
