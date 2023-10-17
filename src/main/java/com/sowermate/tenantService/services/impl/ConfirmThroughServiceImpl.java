package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.ConfirmThroughRepository;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.ConfirmThroughService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ConfirmThroughServiceImpl implements ConfirmThroughService {

    @Autowired
    private ConfirmThroughRepository confirmThroughRepository;

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Override
    public ConfirmThroughValue createConfirmThrough(ConfirmThroughValue confirmThroughValue) {

        TenantEntity tenantEntity = getTenantEntity(confirmThroughValue.getTenantUuid());

        ProFormaInvoiceEntity proFormaInvoiceEntity = getProFormaInvoiceEntity(confirmThroughValue.getTenantUuid(),
                confirmThroughValue.getProFormaInvoiceUuid());

        CompanyEntity firmEntity = getFirmEntity(confirmThroughValue.getTenantUuid(),confirmThroughValue.getFirmUuid());

        ConfirmThroughEntity confirmThroughEntity = confirmThroughValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .firmEntity(firmEntity)
                .proFormaInvoiceEntity(proFormaInvoiceEntity).build();
        return confirmThroughRepository.save(confirmThroughEntity).toDTO();
    }

    @Override
    public List<ConfirmThroughValue> getAllConfirmThrough(String tenantUuid) {
        List<ConfirmThroughValue> confirmThroughValues = new ArrayList<>();
        ConfirmThroughValue confirmThroughValue = null;
        List<ConfirmThroughEntity> confirmThroughEntities = confirmThroughRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return confirmThroughEntities.stream().map(cte -> cte.toDTO()).collect(Collectors.toList());
    }

    @Override
    public ConfirmThroughValue editConfirmThrough(ConfirmThroughValue confirmThroughValue) {
        TenantEntity tenantEntity = getTenantEntity(confirmThroughValue.getTenantUuid());
        CompanyEntity firmEntity = getFirmEntity(confirmThroughValue.getFirmUuid(),
                confirmThroughValue.getFirmUuid());
        ProFormaInvoiceEntity proFormaInvoiceEntity = getProFormaInvoiceEntity(confirmThroughValue.getProFormaInvoiceUuid(),
                confirmThroughValue.getProFormaInvoiceUuid());
        ConfirmThroughEntity tempConfirmThroughEntity = getConfirmThroughEntity(confirmThroughValue.getTenantUuid(), confirmThroughValue.getUuid());

        ConfirmThroughEntity confirmThroughEntity = confirmThroughValue.toEntity().toBuilder()
                .id(tempConfirmThroughEntity.getId())
                .tenantEntity(tenantEntity)
                .firmEntity(firmEntity)
                .proFormaInvoiceEntity(proFormaInvoiceEntity)
                .createdDateTime(tempConfirmThroughEntity.getCreatedDateTime())
                .createdBy(tempConfirmThroughEntity.getCreatedBy())
                .build();
        return confirmThroughRepository.save(confirmThroughEntity).toDTO();
    }

    @Override
    public ConfirmThroughValue getConfirmThrough(String tenantUuid, String confirmThroughUuid) {
        return getConfirmThroughEntity(tenantUuid, confirmThroughUuid).toDTO();
    }

    @Override
    public int deleteConfirmThrough(String tenantUuid, String confirmThroughUuid) {
        ConfirmThroughEntity confirmThroughEntity = getConfirmThroughEntity(tenantUuid,confirmThroughUuid);
        return confirmThroughRepository.deleteByConfirmThroughUuid(confirmThroughUuid);
    }

    public ConfirmThroughEntity getConfirmThroughEntity(String tenantUuid, String confirmThroughUuid){
        ConfirmThroughEntity confirmThroughEntity = confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUuid, confirmThroughUuid);
        if(confirmThroughEntity==null){
            throw new ResourceNotFoundException("ConfirmThroughEntity","tenantUuid or confirmThroughUuid",tenantUuid +" or "+confirmThroughUuid);
        }
        return confirmThroughEntity;
    }

    public TenantEntity getTenantEntity(String tenantUuid){
        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUuid);
        if(tenantEntity==null){
            throw new ResourceNotFoundException("TenantEntity","tenantUuid",tenantUuid);
        }
        return tenantEntity;
    }

    public ProFormaInvoiceEntity getProFormaInvoiceEntity(String tenantUuid, String proFormaInvoiceUuid){
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(tenantUuid,proFormaInvoiceUuid);
        if(proFormaInvoiceEntity==null){
            throw new ResourceNotFoundException("proFormaInvoiceEntity","tenantUuid or proFormaInvoiceUuid",tenantUuid +" or "+proFormaInvoiceUuid);
        }
        return proFormaInvoiceEntity;
    }

    public CompanyEntity getFirmEntity(String tenantUuid, String firmUuid){
        CompanyEntity firmEntity = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUuid,firmUuid);
        if(firmEntity==null){
            throw new ResourceNotFoundException("CompanyEntity","tenantUuid or firmUuid",tenantUuid +" or "+firmUuid);
        }
        return firmEntity;
    }

}
