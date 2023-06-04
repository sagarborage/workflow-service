package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.AdditionalChargesEntity;
import com.sowermate.tenantService.entities.ServiceRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalChargesRepository  extends JpaRepository<AdditionalChargesEntity ,String> {

    public List<AdditionalChargesEntity> findByUuid(@Param("uuid")String uuid);
    public List<AdditionalChargesEntity> findAll();
    public List<AdditionalChargesEntity> deleteAdditionalByUuid(@Param("uuid")String uuid);

}
