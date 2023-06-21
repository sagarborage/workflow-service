package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProFormaInvoiceRepository extends JpaRepository<ProFormaInvoiceEntity, String> {

    @Query("SELECT p FROM ProFormaInvoiceEntity p WHERE p.proFormInvoiceUuid = :proFormInvoiceUuid")
    public ProFormaInvoiceEntity findByProFormaInvoiceUuid(@Param("proFormInvoiceUuid") String proFormInvoiceUuid);

  //  public ProFormaInvoiceEntity deleteByProFormaInvoiceUuid(@Param("proFormInvoiceUuid")String proFormInvoiceUuid);

    public List<ProFormaInvoiceEntity> findAll();
    public List<ProFormaInvoiceEntity> findByProFormaInvoiceId(int proFormaInvoiceId);
    @Modifying
    @Query("DELETE FROM ProFormaInvoiceEntity g WHERE g.proFormInvoiceUuid = :proFormInvoiceUuid")
    void deleteByProFormaInvoiceUuid(@Param("proFormInvoiceUuid") String proFormInvoiceUuid);


}
