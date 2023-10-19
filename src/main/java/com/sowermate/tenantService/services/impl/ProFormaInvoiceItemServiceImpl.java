package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
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
    public ProFormaInvoiceItemValue createProFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) {

        String tenantUuid = proFormaInvoiceItemValue.getTenantUuid();
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemValue.toEntity().toBuilder()
                .tenantEntity(getTenantEntity(tenantUuid))
                .proFormaInvoiceEntity(getProFormaInvoiceEntity(tenantUuid, proFormaInvoiceItemValue.getProFormaInvoiceUuid()))
                .glassThicknessEntity(getGlassThicknessEntity(tenantUuid, proFormaInvoiceItemValue.getGlassThicknessUuid()))
                .glassTypeEntity(getGlassTypeEntity(tenantUuid, proFormaInvoiceItemValue.getGlassTypeUuid()))
                .glassSpecificationEntity(getGlassSpecificationEntity(tenantUuid, proFormaInvoiceItemValue.getGlassSpecificationUuid()))
                .build();
        return proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity).toDTO();
    }

    @Override
    public ProFormaInvoiceItemValue editProFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) {

        String tenantUuid = proFormaInvoiceItemValue.getTenantUuid();
        ProFormaInvoiceItemEntity tempProFormaInvoiceItemEntity = getProFormaInvoiceItemEntity(tenantUuid,proFormaInvoiceItemValue.getUuid());
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemValue.toEntity().toBuilder()
                .id(tempProFormaInvoiceItemEntity.getId())
                .tenantEntity(getTenantEntity(tenantUuid))
                .proFormaInvoiceEntity(getProFormaInvoiceEntity(tenantUuid, proFormaInvoiceItemValue.getProFormaInvoiceUuid()))
                .glassThicknessEntity(getGlassThicknessEntity(tenantUuid, proFormaInvoiceItemValue.getGlassThicknessUuid()))
                .glassTypeEntity(getGlassTypeEntity(tenantUuid, proFormaInvoiceItemValue.getGlassTypeUuid()))
                .glassSpecificationEntity(getGlassSpecificationEntity(tenantUuid, proFormaInvoiceItemValue.getGlassSpecificationUuid()))
                .createdDateTime(tempProFormaInvoiceItemEntity.getCreatedDateTime())
                .createdBy(tempProFormaInvoiceItemEntity.getCreatedBy())
                .build();
        return proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity).toDTO();
    }

    @Override
    public ProFormaInvoiceItemValue getProFormaInvoiceItem(String tenantUuid, String proFormaInvoiceItemUuid) {
        return getProFormaInvoiceItemEntity(tenantUuid,proFormaInvoiceItemUuid).toDTO();
    }

    @Override
    public int deleteProFormaInvoiceItem(String tenantUuid, String ProFormaInvoiceItemUuid) {
        return proFormaInvoiceItemRepository.deleteByUuid(ProFormaInvoiceItemUuid);
    }

    @Override
    public List<ProFormaInvoiceItemValue> getAllProFormaInvoiceItem(String proFormaInvoiceUuid) {
        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities = proFormaInvoiceItemRepository.findAllByProFormaInvoiceEntity_uuid(proFormaInvoiceUuid);
        return proFormaInvoiceItemEntities.stream().map(piie -> piie.toDTO()).collect(Collectors.toList());
    }

    public ProFormaInvoiceItemEntity getProFormaInvoiceItemEntity(String tenantUuid, String proFormaInvoiceItemUuid){
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(tenantUuid,proFormaInvoiceItemUuid);
        if(proFormaInvoiceItemEntity==null){
            throw new ResourceNotFoundException("proFormaInvoiceEntity","tenantUuid or proFormaInvoiceUuid",tenantUuid +" or "+proFormaInvoiceItemUuid);
        }
        return proFormaInvoiceItemEntity;
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

    public GlassThicknessEntity getGlassThicknessEntity(String tenantUuid, String glassThicknessUuid){
        GlassThicknessEntity glassThicknessEntity = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid,glassThicknessUuid);
        if(glassThicknessEntity==null){
            throw new ResourceNotFoundException("proFormaInvoiceEntity","tenantUuid or glassThicknessUuid",tenantUuid +" or "+glassThicknessUuid);
        }
        return glassThicknessEntity;
    }

    public GlassTypeEntity getGlassTypeEntity(String tenantUuid, String glassTypeEntityUuid){
        GlassTypeEntity glassThicknessEntity = glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid,glassTypeEntityUuid);
        if(glassThicknessEntity==null){
            throw new ResourceNotFoundException("proFormaInvoiceEntity","tenantUuid or glassTypeEntityUuid",tenantUuid +" or "+glassTypeEntityUuid);
        }
        return glassThicknessEntity;
    }

    public GlassSpecificationEntity getGlassSpecificationEntity(String tenantUuid, String glassSpecificationEntityUuid){
        GlassSpecificationEntity glassSpecificationEntity = glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid,glassSpecificationEntityUuid);
        if(glassSpecificationEntity==null){
            throw new ResourceNotFoundException("proFormaInvoiceEntity","tenantUuid or glassSpecificationEntityUuid",tenantUuid +" or "+glassSpecificationEntityUuid);
        }
        return glassSpecificationEntity;
    }


}

