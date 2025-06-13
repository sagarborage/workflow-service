package com.sowermate.workflow.service.services.impl;

import com.sowermate.core.tenant.repositories.TenantRepository;
import com.sowermate.core.tenant.services.TenantService;
import com.sowermate.workflow.domain.entities.WorkOrderEntity;
import com.sowermate.workflow.domain.entities.value.WorkOrderValue;
import com.sowermate.workflow.persistence.repositories.ProFormaInvoiceRepository;
import com.sowermate.workflow.persistence.repositories.WorkOrderRepository;
import com.sowermate.workflow.service.services.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private TenantService tenantService;

    @Override
    public WorkOrderValue createWorkOrder(WorkOrderValue workOrderValue) {
        WorkOrderEntity workOrderEntity = workOrderValue.toEntity().toBuilder()
                .firm(tenantService.getTenantEntity(workOrderValue.getFirmUuid()))
                .tenantEntity(tenantRepository.findByUuid(workOrderValue.getTenantUuid()))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(workOrderValue.getTenantUuid(), workOrderValue.getProFormaInvoiceUuid()))
                .build();
        return workOrderRepository.save(workOrderEntity).toDTO();
    }
}
