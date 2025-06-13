package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.GatePassDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GatePassDetailsRepository extends JpaRepository<GatePassDetailsEntity, Long> {

    GatePassDetailsEntity findByUuid(String gatePassDetailsUuid);

    List<GatePassDetailsEntity> findByProFormaInvoiceItemEntityIdAndGatePassEntityId(Long proFormaInvoiceItemId, Long gatePassId);

    @Query("SELECT gpd FROM GatePassDetailsEntity gpd " +
            "JOIN gpd.gatePassEntity gp " +
            "JOIN gpd.proFormaInvoiceItemEntity p " +
            "WHERE p.uuid = :proFormaInvoiceItemUuid " +
            "AND gp.uuid = :gatePassUuid " +
            "AND gpd.uuid =:gatePassDetailsUuid")
    GatePassDetailsEntity findByProFormaInvoiceItemEntity_UuidAndGatePassEntityUuidAndGatePassDetailsUuid(String gatePassDetailsUuid, String proFormaInvoiceItemUuid, String gatePassUuid);

    @Transactional
    @Modifying
    @Query("UPDATE GatePassDetailsEntity g SET g.isActive = false WHERE g.uuid = :gatePassDetailsUuid")
    void softDelete(@Param("gatePassDetailsUuid") String gatePassDetailsUuid);

//    @Modifying
//    @Query("DELETE FROM GatePassDetailsEntity g WHERE g.gatePassDetailsUuid = :gatePassDetailsUuid AND g.proFormaInvoiceItemUuid =:proFormaInvoiceItemUuid AND g.gatePassUuid =:gatePassUuid")
//    int deleteByGatePassDetailsUuid(@Param("gatePassDetailsUuid") String gatePassDetailsUuid, @Param("proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid, @Param("gatePassUuid") String gatePassUuid);
}
