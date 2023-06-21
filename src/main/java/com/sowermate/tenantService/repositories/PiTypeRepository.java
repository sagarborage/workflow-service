package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.PiTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PiTypeRepository extends JpaRepository<PiTypeEntity, String> {

    public PiTypeEntity findByPiTypeUuid(@Param("piTypeUuid")String piTypeUuid);

    public PiTypeEntity findByPiTypeId(int piTypeId);

    public PiTypeEntity deleteByPiTypeUuid(@Param("piTypeUuid")String piTypeUuid);
}
