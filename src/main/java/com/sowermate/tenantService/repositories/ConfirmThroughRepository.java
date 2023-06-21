package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ConfirmThroughRepository extends JpaRepository<ConfirmThroughEntity, String> {

    public ConfirmThroughEntity findByConfirmThroughUuid(@Param("confirmThroughUuid")String confirmThroughUuid);

    public List<ConfirmThroughEntity> findByConfirmThroughId(int confirmThroughId);

    public ConfirmThroughEntity deleteByConfirmThroughUuid(@Param("confirmThroughUuid")String confirmThroughUuid);




}
