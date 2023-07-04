package com.sowermate.tenantService.repositories;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProFormaInvoiceItemRepository extends JpaRepository<ProFormaInvoiceItemEntity, String> {

  //  @Query("SELECT p FROM ProFormaInvoiceItemEntity p WHERE p.proFormaInvoiceItemUuid = :proFormaInvoiceItemUuid")
   // public ProFormaInvoiceItemEntity findByProFormaInvoiceItemUuid( String proFormaInvoiceItemUuid);

   // public ProFormaInvoiceItemEntity deleteByUuid(String uuid);

  //  public List<ProFormaInvoiceItemEntity> findAll();
  //  public List<ProFormaInvoiceItemEntity> findByProFormaInvoiceItemId(int proFormaInvoiceItemId);

    @Query("SELECT p FROM ProFormaInvoiceItemEntity p " +
            "JOIN p.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND p.proFormaInvoiceItemUuid = :proFormaInvoiceItemUuid")
    public ProFormaInvoiceItemEntity findByTenantEntity_UuidAndProFormaInvoiceItemUuid(@Param("tenantUuid") String tenantUuid, @Param("proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid);

    public List<ProFormaInvoiceItemEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    @Modifying
    @Query("DELETE FROM ProFormaInvoiceItemEntity p WHERE p.proFormaInvoiceItemUuid = :proFormaInvoiceItemUuid")
    int deleteByProFormaInvoiceItemUuid(@Param("proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid);


}
