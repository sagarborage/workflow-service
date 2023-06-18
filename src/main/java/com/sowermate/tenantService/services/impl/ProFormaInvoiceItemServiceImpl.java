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

    @Override
    public ProFormaInvoiceItemValue createProFormInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) throws Exception {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=new ProFormaInvoiceItemEntity();
        BeanUtils.copyProperties(proFormaInvoiceItemValue ,proFormaInvoiceItemEntity);
        String randomProFormaInvoiceItemId= UUID.randomUUID().toString();
        proFormaInvoiceItemEntity.setUuid(randomProFormaInvoiceItemId);
        proFormaInvoiceItemEntity.setProFormaInvoiceEntity(proFormaInvoiceRepository.findByUuid(proFormaInvoiceItemValue.getProFormaInvoiceUUID()).get(0));
        proFormaInvoiceItemEntity.setGlassTypeEntity(glassTypeRepository.findByUuid(proFormaInvoiceItemValue.getGlassTypeUUID()).get(0));
        proFormaInvoiceItemEntity.setGlassSpecificationEntity(glassSpecificationRepository.findByUuid(proFormaInvoiceItemValue.getGlassSpecificationUUID()).get(0));
        proFormaInvoiceItemEntity.setGlassThicknessEntity(glassThicknessRepository.findByUuid(proFormaInvoiceItemValue.getGlassThicknessUUID()).get(0));
        BeanUtils.copyProperties(proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity), proFormaInvoiceItemValue);
        return proFormaInvoiceItemValue;
    }
    @Override
    public ProFormaInvoiceItemValue editProFormInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) throws Exception {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=new ProFormaInvoiceItemEntity();
        BeanUtils.copyProperties(proFormaInvoiceItemValue , proFormaInvoiceItemEntity);
        proFormaInvoiceItemEntity.setProFormaInvoiceEntity(proFormaInvoiceRepository.findByUuid(proFormaInvoiceItemValue.getProFormaInvoiceUUID()).get(0));
        proFormaInvoiceItemEntity.setGlassTypeEntity(glassTypeRepository.findByUuid(proFormaInvoiceItemValue.getGlassTypeUUID()).get(0));
        proFormaInvoiceItemEntity.setGlassSpecificationEntity(glassSpecificationRepository.findByUuid(proFormaInvoiceItemValue.getGlassSpecificationUUID()).get(0));
        proFormaInvoiceItemEntity.setGlassThicknessEntity(glassThicknessRepository.findByUuid(proFormaInvoiceItemValue.getGlassThicknessUUID()).get(0));
        proFormaInvoiceItemEntity.setProFormaInvoiceItemId(proFormaInvoiceItemRepository.findByUuid(proFormaInvoiceItemValue.getUuid()).get(0).getProFormaInvoiceItemId());
        BeanUtils.copyProperties(proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity), proFormaInvoiceItemValue);
        return proFormaInvoiceItemValue;
    }

    @Override
    public ProFormaInvoiceItemValue getProFormInvoiceItem(String uuid) throws Exception {
        ProFormaInvoiceItemValue proFormaInvoiceItemValue =new ProFormaInvoiceItemValue();
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=proFormaInvoiceItemRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(proFormaInvoiceItemEntity, proFormaInvoiceItemValue);
        return proFormaInvoiceItemValue;
    }

    @Override
    public ProFormaInvoiceItemValue deleteProFormInvoiceItem(String uuid) throws Exception {
        ProFormaInvoiceItemValue proFormaInvoiceItemValue=new ProFormaInvoiceItemValue();
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity =proFormaInvoiceItemRepository.deleteByUuid(uuid) .get(0);
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

