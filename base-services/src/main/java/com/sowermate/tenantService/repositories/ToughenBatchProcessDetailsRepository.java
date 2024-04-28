package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.ToughenBatchProcessDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToughenBatchProcessDetailsRepository extends JpaRepository<ToughenBatchProcessDetailsEntity, String> {
}
