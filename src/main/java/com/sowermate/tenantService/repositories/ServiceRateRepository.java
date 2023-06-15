package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.ServiceRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRateRepository extends JpaRepository<ServiceRateEntity, String>{

    public List<ServiceRateEntity> findByUuid(@Param("uuid")String uuid);
    public List<ServiceRateEntity> deleteServiceByUuid(@Param("uuid")String uuid);
    public List<ServiceRateEntity> findAll();

    public List<ServiceRateEntity> findByServiceRateId(int serviceRateId);
}
