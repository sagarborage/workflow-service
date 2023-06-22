package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.ProFormaInvoiceItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName= {"Exception"})
public class ProFormaInvoiceItemServiceImpl implements ProFormaInvoiceItemService {

    @Autowired
    private ProFormaInvoiceItemRepository  proFormaInvoiceItemRepository;

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    private GlassTypeRepository glassTypeRepository;

    @Autowired
    private GlassSpecificationRepository  glassSpecificationRepository;

    @Autowired
    private GlassThicknessRepository  glassThicknessRepository;

    @Autowired
    private TenantRepository  tenantRepository;

    @Override
    public ProFormaInvoiceItemValue createProFormInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) throws Exception {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=new ProFormaInvoiceItemEntity();
        BeanUtils.copyProperties(proFormaInvoiceItemValue ,proFormaInvoiceItemEntity);
        String randomProFormaInvoiceItemUuid= UUID.randomUUID().toString();
        proFormaInvoiceItemEntity.setProFormaInvoiceItemUuid(randomProFormaInvoiceItemUuid);
        proFormaInvoiceItemEntity.setProFormaInvoiceEntity(proFormaInvoiceRepository.findByProFormaInvoiceUuid(proFormaInvoiceItemValue.getProFormaInvoiceUUID()));
        proFormaInvoiceItemEntity.setGlassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getGlassTypeUUID()));// Need to pass tenant uuid
        proFormaInvoiceItemEntity.setGlassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getGlassSpecificationUUID()));
        proFormaInvoiceItemEntity.setGlassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getGlassThicknessUUID()));
        BeanUtils.copyProperties(proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity), proFormaInvoiceItemValue);
        return proFormaInvoiceItemValue;
    }
    @Override
    public ProFormaInvoiceItemValue editProFormInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) throws Exception {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=new ProFormaInvoiceItemEntity();
        BeanUtils.copyProperties(proFormaInvoiceItemValue , proFormaInvoiceItemEntity);
        proFormaInvoiceItemEntity.setProFormaInvoiceEntity(proFormaInvoiceRepository.findByProFormaInvoiceUuid(proFormaInvoiceItemValue.getProFormaInvoiceUUID()));
        proFormaInvoiceItemEntity.setGlassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(null, proFormaInvoiceItemValue.getGlassTypeUUID()));// Need to pass tenant uuid
        proFormaInvoiceItemEntity.setGlassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getGlassSpecificationUUID()));
        proFormaInvoiceItemEntity.setGlassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getGlassThicknessUUID()));
        proFormaInvoiceItemEntity.setProFormaInvoiceItemId(proFormaInvoiceItemRepository.findByProFormaInvoiceItemUuid(proFormaInvoiceItemValue.getProFormaInvoiceItemUuid()).getProFormaInvoiceItemId());
        BeanUtils.copyProperties(proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity), proFormaInvoiceItemValue);
        return proFormaInvoiceItemValue;
    }

    @Override
    public ProFormaInvoiceItemValue getProFormInvoiceItem(String ProFormaInvoiceItemUuid) throws Exception {
        ProFormaInvoiceItemValue proFormaInvoiceItemValue =new ProFormaInvoiceItemValue();
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=proFormaInvoiceItemRepository.findByProFormaInvoiceItemUuid(ProFormaInvoiceItemUuid);
        BeanUtils.copyProperties(proFormaInvoiceItemEntity, proFormaInvoiceItemValue);
        return proFormaInvoiceItemValue;
    }

    @Override
    public ProFormaInvoiceItemValue deleteProFormInvoiceItem(String ProFormaInvoiceItemUuid) throws Exception {
        ProFormaInvoiceItemValue proFormaInvoiceItemValue=new ProFormaInvoiceItemValue();
        proFormaInvoiceItemRepository.deleteByProFormaInvoiceItemUuid(ProFormaInvoiceItemUuid);
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity =proFormaInvoiceItemRepository.findByProFormaInvoiceItemUuid(ProFormaInvoiceItemUuid);
        BeanUtils.copyProperties( proFormaInvoiceItemEntity, proFormaInvoiceItemValue);
        return  proFormaInvoiceItemValue;
    }

    @Override
    public List<ProFormaInvoiceItemValue> getAllProFormInvoiceItem() throws Exception {
        List<ProFormaInvoiceItemValue> proFormaInvoiceItemValues = new ArrayList<>();
        ProFormaInvoiceItemValue proFormaInvoiceItemValue = null;
        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities = proFormaInvoiceItemRepository.findAll();
        for (int i = 0; i < proFormaInvoiceItemEntities.size(); i++) {
            proFormaInvoiceItemValue = new ProFormaInvoiceItemValue();
            BeanUtils.copyProperties(proFormaInvoiceItemEntities.get(i), proFormaInvoiceItemValue);
            proFormaInvoiceItemValues.add(proFormaInvoiceItemValue);
        }
        return proFormaInvoiceItemValues;
    }
}

