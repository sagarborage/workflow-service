package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ProFormaInvoiceRepository extends JpaRepository<ProFormaInvoiceEntity, String> {


    @Query("SELECT p FROM ProFormaInvoiceEntity p " +
            "JOIN p.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND p.proFormInvoiceUuid = :proFormInvoiceUuid")
    public ProFormaInvoiceEntity findByTenantEntity_UuidAndProFormInvoiceUuid(@Param("tenantUuid") String tenantUuid, @Param("proFormInvoiceUuid") String proFormInvoiceUuid);

    public List<ProFormaInvoiceEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    @Transactional
    @Modifying
   // @Query("DELETE FROM ProFormaInvoiceEntity g WHERE g.proFormInvoiceUuid = :proFormInvoiceUuid")
    //int deleteByProFormaInvoiceUuid(@Param("proFormInvoiceUuid") String proFormInvoiceUuid);

    @Query("UPDATE ProFormaInvoiceEntity p SET p.isActive = false WHERE p.proFormInvoiceUuid = :proFormInvoiceUuid")
    void softDelete(@Param("proFormInvoiceUuid") String tenantUuid);

}
