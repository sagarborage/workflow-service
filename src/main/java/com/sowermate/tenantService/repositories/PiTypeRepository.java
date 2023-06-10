package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.PiTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PiTypeRepository extends JpaRepository<PiTypeEntity, String> {

    public List<PiTypeEntity> findByUuid(@Param("uuid")String uuid);

    public List<PiTypeEntity> findByPiTypeId(int piTypeId);

    public List<PiTypeEntity> deletePiTypeByUuid(@Param("uuid")String uuid);
}
