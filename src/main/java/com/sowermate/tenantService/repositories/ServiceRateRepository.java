package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.ServiceRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRateRepository extends JpaRepository<ServiceRateEntity, String>{

    public ServiceRateEntity findByServiceRateUuid(@Param("serviceRateUuid")String serviceRateUuid);
    public ServiceRateEntity deleteByServiceRateUuid(@Param("serviceRateUuid")String serviceRateUuid);
    public List<ServiceRateEntity> findAll();

    public List<ServiceRateEntity> findByServiceRateId(int serviceRateId);
}
