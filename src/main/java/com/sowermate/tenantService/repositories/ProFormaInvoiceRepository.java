package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProFormaInvoiceRepository extends JpaRepository<ProFormaInvoiceEntity, String> {

    @Query("SELECT p FROM ProFormaInvoiceEntity p WHERE p.uuid = :uuid")
    public List<ProFormaInvoiceEntity> findByUuid(@Param("uuid") String uuid);

    public List<ProFormaInvoiceEntity> deleteByUuid(@Param("uuid")String uuid);

    public List<ProFormaInvoiceEntity> findAll();
    public List<ProFormaInvoiceEntity> findByProFormaInvoiceId(int proFormaInvoiceId);


}
