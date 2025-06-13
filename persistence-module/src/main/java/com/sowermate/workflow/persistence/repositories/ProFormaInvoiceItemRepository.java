package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.ProFormaInvoiceItemEntity;
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

/*
    @Query("SELECT p FROM ProFormaInvoiceItemEntity p " +
            "JOIN p.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND p.proFormaInvoiceItemUuid = :proFormaInvoiceItemUuid")
    public ProFormaInvoiceItemEntity findByTenantEntity_UuidAndProFormaInvoiceItemUuid(@Param("tenantUuid") String tenantUuid, @Param("proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid);
*/

    //TODO: use proFormaInvoiceUuid as well
    public ProFormaInvoiceItemEntity findByUuid(String uuid);

    @Query("SELECT p FROM ProFormaInvoiceItemEntity p " +
            "JOIN p.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND p.uuid = :proFormaInvoiceItemUuid")
    ProFormaInvoiceItemEntity findByTenantEntity_UuidAndProFormaInvoiceItemUuid(@Param("tenantUuid") String tenantUuid, @Param("proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid);

    @Query("SELECT p FROM ProFormaInvoiceItemEntity p " +
            "JOIN p.tenantEntity t " +
            "JOIN p.proFormaInvoiceEntity pi " +
            "WHERE t.uuid = :tenantUuid " +
            "AND pi.uuid = :proFormaInvoiceUuid")
    List<ProFormaInvoiceItemEntity> findByTenantEntity_UuidAndProFormaInvoiceUuid(@Param("tenantUuid") String tenantUuid, @Param("proFormaInvoiceUuid") String proFormaInvoiceUuid);


    public List<ProFormaInvoiceItemEntity> findAllByProFormaInvoiceEntity_uuid(String proFormaInvoiceUuid);

    public List<ProFormaInvoiceItemEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    @Modifying
    @Query("DELETE FROM ProFormaInvoiceItemEntity p WHERE p.uuid = :proFormaInvoiceItemUuid")
    int deleteByUuid(@Param("proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid);
}
