package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.WorkOrderEntity;
import com.sowermate.tenantService.entities.value.WorkOrderValue;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.repositories.WorkOrderRepository;
import com.sowermate.tenantService.services.WorkOrderService;
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

    @Override
    public WorkOrderValue createWorkOrder(WorkOrderValue workOrderValue) {
        String tenantUuid = workOrderValue.getTenantUuid();
        CompanyEntity firm = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUuid, workOrderValue.getFirmUuid());
        TenantEntity tenant = tenantRepository.findByUuid(tenantUuid);
        ProFormaInvoiceEntity proFormaInvoice = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(tenantUuid, workOrderValue.getProFormaInvoiceUuid());

        WorkOrderEntity workOrderEntity = workOrderValue.toEntity().toBuilder()
                .firm(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUuid, workOrderValue.getFirmUuid()))
                .tenantEntity(tenantRepository.findByUuid(tenantUuid))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(tenantUuid, workOrderValue.getProFormaInvoiceUuid()))
                .build();
        return workOrderRepository.save(workOrderEntity).toDTO();
    }
}
