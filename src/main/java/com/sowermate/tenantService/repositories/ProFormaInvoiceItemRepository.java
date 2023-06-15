package com.sowermate.tenantService.repositories;
import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProFormaInvoiceItemRepository extends JpaRepository<ProFormaInvoiceItemEntity, String> {

    @Query("SELECT p FROM ProFormaInvoiceItemEntity p WHERE p.uuid = :uuid")
    public List<ProFormaInvoiceItemEntity> findByUuid(@Param("uuid") String uuid);

    public List<ProFormaInvoiceItemEntity> deleteByUuid(@Param("uuid")String uuid);

    public List<ProFormaInvoiceItemEntity> findAll();
    public List<ProFormaInvoiceItemEntity> findByProFormaInvoiceItemId(int proFormaInvoiceItemId);

}
