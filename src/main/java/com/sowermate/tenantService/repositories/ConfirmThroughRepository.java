package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ConfirmThroughRepository extends JpaRepository<ConfirmThroughEntity, String> {

    public List<ConfirmThroughEntity> findByUuid(@Param("uuid")String uuid);

    public List<ConfirmThroughEntity> findByConfirmThroughId(int confirmThroughId);

    public List<ConfirmThroughEntity> deleteConfirmThroughByUuid(@Param("uuid")String uuid);

}
