package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.GlassBreakageDetailsValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.GlassBreakageDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class GlassBreakageDetailsServiceImpl implements GlassBreakageDetailsService {

    @Autowired
    private GlassBreakageDetailsRepository glassBreakageDetailsRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    private ProFormaInvoiceItemRepository proFormaInvoiceItemRepository;

    @Override
    public GlassBreakageDetailsValue createGlassBreakageDetails(GlassBreakageDetailsValue glassBreakageDetailsValue) {

        TenantEntity tenantEntity = tenantRepository.findByUuid(glassBreakageDetailsValue.getTenantUuid());
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByUuid(glassBreakageDetailsValue.getProFormaInvoiceUuid());
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByUuid(glassBreakageDetailsValue.getProFormaInvoiceItemUuid());

        GlassBreakageDetailsEntity glassBreakageDetailsEntity = glassBreakageDetailsValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .proFormaInvoiceEntity(proFormaInvoiceEntity)
                .proFormaInvoiceItemEntity(proFormaInvoiceItemEntity)
                .deptName(glassBreakageDetailsValue.getDeptName())
                .details(glassBreakageDetailsValue.getDetails())
                .isActive(true)
                .build();
        return glassBreakageDetailsRepository.save(glassBreakageDetailsEntity).toDTO();
    }

    @Override
    public List<GlassBreakageDetailsValue> getAllGlassBreakageDetails(String tenantUuid, String companyUuid, String proFormaInvoiceUuid, String workOrderUuid, String proFormaInvoiceItemUuid) {
        List<GlassBreakageDetailsValue> glassBreakageDetailsValues = new ArrayList<>();
        GlassBreakageDetailsValue glassBreakageDetailsValue = null;
        List<GlassBreakageDetailsEntity> glassBreakageDetailsEntities = glassBreakageDetailsRepository.findAllByTenantEntityUuidAndProFormaInvoiceItemEntityUuid(tenantUuid, proFormaInvoiceItemUuid);
        return glassBreakageDetailsEntities.stream().map(GlassBreakageDetailsEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public GlassBreakageDetailsValue editGlassBreakageDetails(GlassBreakageDetailsValue glassBreakageDetailsValue) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(glassBreakageDetailsValue.getTenantUuid());
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByUuid(glassBreakageDetailsValue.getProFormaInvoiceUuid());
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByUuid(glassBreakageDetailsValue.getProFormaInvoiceItemUuid());

        GlassBreakageDetailsEntity glassBreakageDetailsEntity = glassBreakageDetailsRepository
                .findByUuidAndProfileInvoiceItemUuid(glassBreakageDetailsValue.getUuid(), glassBreakageDetailsValue.getProFormaInvoiceItemUuid());

        GlassBreakageDetailsEntity glassBreakageDetails = glassBreakageDetailsValue.toEntity().toBuilder()
                .id(glassBreakageDetailsEntity.getId())
                .tenantEntity(tenantEntity)
                .proFormaInvoiceEntity(proFormaInvoiceEntity)
                .proFormaInvoiceItemEntity(proFormaInvoiceItemEntity)
                .createdDateTime(glassBreakageDetailsEntity.getCreatedDateTime())
                .deptName(glassBreakageDetailsValue.getDeptName())
                .details(glassBreakageDetailsValue.getDetails())
                .version(glassBreakageDetailsEntity.getVersion())
                .createdBy(glassBreakageDetailsEntity.getCreatedBy())
                .build();
        return glassBreakageDetailsRepository.save(glassBreakageDetails).toDTO();
    }


    @Override
    public GlassBreakageDetailsValue getGlassBreakageDetails(String glassBreakageDetailsUuid, String proFormaInvoiceItemUuid) {
        return glassBreakageDetailsRepository.findByUuidAndProfileInvoiceItemUuid(glassBreakageDetailsUuid, proFormaInvoiceItemUuid).toDTO();
    }

    @Override
    public int deleteGlassBreakageDetails(String glassBreakageDetailsUuid, String tenantUuid, String companyUuid, String proFormaInvoiceUuid, String workOrderUuid, String proFormaInvoiceItemUuid) {
        return glassBreakageDetailsRepository.deleteByGlassBreakageDetailsUuid(glassBreakageDetailsUuid);
    }

}
