package com.sowermate.workflow.persistence.repositories;

import com.sowermate.workflow.domain.entities.WorkOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrderEntity, String> {

    WorkOrderEntity findByUuid(String uuid);
}
