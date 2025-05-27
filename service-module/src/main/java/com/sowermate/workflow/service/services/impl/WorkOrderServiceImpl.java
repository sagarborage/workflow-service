package com.sowermate.workflow.service.services.impl;

import com.sowermate.core.tenant.entities.Company;
import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.core.tenant.repositories.CompanyRepository;
import com.sowermate.core.tenant.repositories.TenantRepository;
import com.sowermate.core.tenant.services.CompanyService;
import com.sowermate.workflow.domain.entities.ProFormaInvoiceEntity;
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
    private CompanyRepository companyRepository;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private CompanyService companyService;

    @Override
    public WorkOrderValue createWorkOrder(WorkOrderValue workOrderValue) {
        String tenantUuid = workOrderValue.getTenantUuid();
        Company firm = companyService.getCompanyEntity(workOrderValue.getFirmUuid(), tenantUuid);
        Tenant tenant = tenantRepository.findByUuid(tenantUuid);
        ProFormaInvoiceEntity proFormaInvoice = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(tenantUuid, workOrderValue.getProFormaInvoiceUuid());


        WorkOrderEntity workOrderEntity = workOrderValue.toEntity().toBuilder()
                .firm(companyService.getCompanyEntity(workOrderValue.getFirmUuid(), tenantUuid))
                .tenantEntity(tenantRepository.findByUuid(tenantUuid))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(tenantUuid, workOrderValue.getProFormaInvoiceUuid()))
                .build();
        return workOrderRepository.save(workOrderEntity).toDTO();
    }
}
