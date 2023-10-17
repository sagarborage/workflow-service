package com.sowermate.tenantService.repositories;
import com.sowermate.tenantService.entities.CompanyEntity;
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

//    @Query("SELECT p FROM ProFormaInvoiceItemEntity p " +
//            "JOIN p.tenantEntity t " +
//            "WHERE t.uuid = :tenantUuid " +
//            "AND p.proFormaInvoiceItemUuid = :proFormaInvoiceItemUuid")
//    public ProFormaInvoiceItemEntity findByTenantEntity_UuidAndProFormaInvoiceItemUuid(String tenantUuid, String proFormaInvoiceItemUuid);

//    @Query("SELECT c FROM CompanyEntity c " +
//            "JOIN c.tenantEntity t " +
//            "WHERE t.uuid = :tenantUuid " +
//            "AND c.uuid = :companyUuid")
//    CompanyEntity findByTenantEntity_UuidAndCompanyUuid(String tenantUuid, String companyUuid);
    //TODO: use proFormaInvoiceUuid as well
    public ProFormaInvoiceItemEntity findByUuid(String uuid);

    public List<ProFormaInvoiceItemEntity> findAllByProFormaInvoiceEntity_uuid(String proFormaInvoiceUuid);
    public List<ProFormaInvoiceItemEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    @Modifying
    @Query("DELETE FROM ProFormaInvoiceItemEntity p WHERE p.uuid = :proFormaInvoiceItemUuid")
    int deleteByUuid(@Param("proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid);


}
