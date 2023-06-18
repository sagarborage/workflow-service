package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, String> {
    public List<StatusEntity> findByUuid(@Param("uuid")String uuid);

    public List<StatusEntity> findByStatusId(int statusId);

    @Transactional
    @Modifying
    @Query("UPDATE StatusEntity s SET s.isActive = false WHERE s.uuid = :uuid")
    void softDelete(@Param("uuid") String uuid);
}
