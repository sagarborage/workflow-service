package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.PiTypeEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProFormaInvoiceRepository extends JpaRepository<ProFormaInvoiceEntity, String> {

    // @Query("SELECT p FROM ProFormaInvoiceEntity p WHERE p.proFormaInvoiceUuid = :proFormaInvoiceUuid")
    // public ProFormaInvoiceEntity findByProFormaInvoiceUuid(@Param("proFormaInvoiceUuid") String proFormaInvoiceUuid);

    //  public ProFormaInvoiceEntity deleteByProFormaInvoiceUuid(@Param("proFormaInvoiceUuid")String proFormaInvoiceUuid);

    //   public List<ProFormaInvoiceEntity> findAll();
    // public List<ProFormaInvoiceEntity> findByProFormaInvoiceId(int proFormaInvoiceId);


    @Query("SELECT p FROM ProFormaInvoiceEntity p " +
            "JOIN p.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND p.uuid = :proFormaInvoiceUuid")
    ProFormaInvoiceEntity findByTenantEntity_UuidAndproFormaInvoiceUuid(@Param("tenantUuid") String tenantUuid, @Param("proFormaInvoiceUuid") String proFormaInvoiceUuid);

    Optional<ProFormaInvoiceEntity> findById(String proFormaInvoiceId);

    ProFormaInvoiceEntity findFirstByTenantEntityIdOrderByCreatedDateTimeDesc(long id);

    ProFormaInvoiceEntity findByUuid(String uuid);
    List<ProFormaInvoiceEntity> findAllByTenantEntity_Id(long tenantId);

    @Modifying
    @Query("DELETE FROM ProFormaInvoiceEntity g WHERE g.uuid = :proFormaInvoiceUuid")
    int deleteByUuid(@Param("proFormaInvoiceUuid") String proFormaInvoiceUuid);

}
