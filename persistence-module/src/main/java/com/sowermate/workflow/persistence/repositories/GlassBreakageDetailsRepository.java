package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.GlassBreakageDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GlassBreakageDetailsRepository extends JpaRepository<GlassBreakageDetailsEntity, String> {
    List<GlassBreakageDetailsEntity> findAllByTenantEntityUuidAndProFormaInvoiceItemEntityUuid(String tenantUuid, String proFormaInvoiceItemUuid);

    @Query("SELECT g FROM GlassBreakageDetailsEntity g " +
            "JOIN g.proFormaInvoiceItemEntity pf " +
            "WHERE pf.uuid = :proFormaInvoiceItemUuid " +
            "And g.uuid = :glassBreakageDetailsUuid")
    public GlassBreakageDetailsEntity findByUuidAndProfileInvoiceItemUuid(@Param("glassBreakageDetailsUuid") String glassBreakageDetailsUuid, @Param("proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid);

    @Modifying
    @Query("DELETE FROM GlassBreakageDetailsEntity g WHERE g.uuid = :glassBreakageDetailsUuid")
    int deleteByGlassBreakageDetailsUuid(@Param("glassBreakageDetailsUuid") String glassBreakageDetailsUuid);
}
