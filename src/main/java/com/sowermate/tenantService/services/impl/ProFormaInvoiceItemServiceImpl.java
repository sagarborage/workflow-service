package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.ProFormaInvoiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ProFormaInvoiceItemServiceImpl implements ProFormaInvoiceItemService {

    @Autowired
    private ProFormaInvoiceItemRepository proFormaInvoiceItemRepository;

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    private GlassTypeRepository glassTypeRepository;

    @Autowired
    private GlassSpecificationRepository glassSpecificationRepository;

    @Autowired
    private GlassThicknessRepository glassThicknessRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public ProFormaInvoiceItemValue createproFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) {

        String tenantUuid = proFormaInvoiceItemValue.getTenantUuid();
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemValue.toEntity().toBuilder()
                .tenantEntity(tenantRepository.findByUuid(tenantUuid))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(
                        tenantUuid, proFormaInvoiceItemValue.getProFormaInvoiceUuid()))
                .glassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassThicknessUuid()))
                .glassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassTypeUuid()))
                .glassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassSpecificationUuid()))
                .build();
        return proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity).toDTO();
    }

    @Override
    public ProFormaInvoiceItemValue editproFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) {

        String tenantUuid = proFormaInvoiceItemValue.getTenantUuid();
        ProFormaInvoiceItemEntity tempProFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByUuid(proFormaInvoiceItemValue.getUuid());
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemValue.toEntity().toBuilder()
                .id(tempProFormaInvoiceItemEntity.getId())
                .tenantEntity(tenantRepository.findByUuid(tenantUuid))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(
                        tenantUuid, proFormaInvoiceItemValue.getProFormaInvoiceUuid()))
                .glassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassThicknessUuid()))
                .glassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassTypeUuid()))
                .glassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassSpecificationUuid()))
                .build();
        return proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity).toDTO();
    }

    @Override
    public ProFormaInvoiceItemValue getproFormaInvoiceItem(String tenantUuid, String proFormaInvoiceItemUuid) {
        return proFormaInvoiceItemRepository.findByUuid(proFormaInvoiceItemUuid).toDTO();
    }

    @Override
    public int deleteproFormaInvoiceItem(String tenantUuid, String ProFormaInvoiceItemUuid) {
        return proFormaInvoiceItemRepository.deleteByUuid(ProFormaInvoiceItemUuid);
    }

    @Override
    public List<ProFormaInvoiceItemValue> getAllproFormaInvoiceItem(String proFormaInvoiceUuid) {
        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities = proFormaInvoiceItemRepository.findAllByProFormaInvoiceEntity_uuid(proFormaInvoiceUuid);
        return proFormaInvoiceItemEntities.stream().map(piie -> piie.toDTO()).collect(Collectors.toList());
    }
}

