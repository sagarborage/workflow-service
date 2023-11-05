package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.WorkOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrderEntity, String> {
}
