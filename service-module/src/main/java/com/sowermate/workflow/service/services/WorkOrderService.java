package com.sowermate.workflow.service.services;

import com.sowermate.workflow.domain.entities.value.WorkOrderValue;

public interface WorkOrderService {
    public WorkOrderValue createWorkOrder(WorkOrderValue workOrderValue);
}
