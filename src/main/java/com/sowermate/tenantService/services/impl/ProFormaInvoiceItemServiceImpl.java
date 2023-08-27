package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.CompanyAddressEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.ProFormaInvoiceItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

        String tenantUuid = proFormaInvoiceItemValue.getTenantValue().getUuid();
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemValue.toEntity().toBuilder()
                .uuid(CommonUtils.generateUUID())
                .tenantEntity(tenantRepository.findByTenantUuid(tenantUuid))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(
                        tenantUuid,proFormaInvoiceItemValue.getProFormaInvoiceValue().getProFormInvoiceUuid()))
                .glassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassThicknessValue().getGlassThicknessUuid()))
                .glassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassTypeValue().getGlassTypeUuid()))
                .glassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassSpecificationValue().getGlassSpecificationUuid()))
                .build();
        return proFormaInvoiceItemEntity.toDTO();
/*
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = new ProFormaInvoiceItemEntity();
        BeanUtils.copyProperties(proFormaInvoiceItemValue ,proFormaInvoiceItemEntity);
        proFormaInvoiceItemEntity.setProFormaInvoiceItemUuid(CommonUtils.generateUUID());
        proFormaInvoiceItemEntity.setTenantEntity(tenantRepository.findByTenantUuid(proFormaInvoiceItemValue.getTenantUuid()));
        proFormaInvoiceItemEntity.setProFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(
                proFormaInvoiceItemValue.getTenantUuid(),proFormaInvoiceItemValue.getProFormaInvoiceUuid()));
        proFormaInvoiceItemEntity.setGlassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid, proFormaInvoiceItemValue.getGlassTypeUuid()));
        proFormaInvoiceItemEntity.setGlassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getGlassSpecificationUuid()));
        proFormaInvoiceItemEntity.setGlassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getGlassThicknessUuid()));
        BeanUtils.copyProperties(proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity), proFormaInvoiceItemValue);*/
    }
    @Override
    public ProFormaInvoiceItemValue editProFormInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) throws Exception {

        String tenantUuid = proFormaInvoiceItemValue.getTenantValue().getUuid();
        ProFormaInvoiceItemEntity tempProFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByUuid(proFormaInvoiceItemValue.getUuid());
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemValue.toEntity().toBuilder()
                .proFormaInvoiceItemId(tempProFormaInvoiceItemEntity.getProFormaInvoiceItemId())
                .tenantEntity(tenantRepository.findByTenantUuid(tenantUuid))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(
                        tenantUuid,proFormaInvoiceItemValue.getProFormaInvoiceValue().getProFormInvoiceUuid()))
                .glassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassThicknessValue().getGlassThicknessUuid()))
                .glassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassTypeValue().getGlassTypeUuid()))
                .glassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassSpecificationValue().getGlassSpecificationUuid()))
                .build();
        return proFormaInvoiceItemEntity.toDTO();

/*        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=new ProFormaInvoiceItemEntity();
        BeanUtils.copyProperties(proFormaInvoiceItemValue , proFormaInvoiceItemEntity);
        proFormaInvoiceItemEntity.setTenantEntity(tenantRepository.findByTenantUuid(proFormaInvoiceItemValue.getTenantUuid()));
        proFormaInvoiceItemEntity.setProFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(proFormaInvoiceItemValue.getTenantUuid(),proFormaInvoiceItemValue.getProFormaInvoiceUuid()));
        proFormaInvoiceItemEntity.setGlassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getGlassTypeUuid()));// Need to pass tenant uuid
        proFormaInvoiceItemEntity.setGlassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getGlassSpecificationUuid()));
        proFormaInvoiceItemEntity.setGlassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getGlassThicknessUuid()));
        proFormaInvoiceItemEntity.setProFormaInvoiceItemId(proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(proFormaInvoiceItemValue.getTenantUuid(),proFormaInvoiceItemValue.getProFormaInvoiceItemUuid()).getProFormaInvoiceItemId());
        BeanUtils.copyProperties(proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity), proFormaInvoiceItemValue);
        return proFormaInvoiceItemValue;*/
    }

    @Override
    public ProFormaInvoiceItemValue getProFormInvoiceItem(String tenantUuid,String proFormaInvoiceItemUuid) throws Exception {
        /*ProFormaInvoiceItemValue proFormaInvoiceItemValue =new ProFormaInvoiceItemValue();
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(tenantUuid,proFormaInvoiceItemUuid);
        BeanUtils.copyProperties(proFormaInvoiceItemEntity.getProFormaInvoiceEntity(), proFormaInvoiceItemValue);
        BeanUtils.copyProperties(proFormaInvoiceItemEntity.getGlassTypeEntity(), proFormaInvoiceItemValue);
        BeanUtils.copyProperties(proFormaInvoiceItemEntity.getGlassSpecificationEntity(), proFormaInvoiceItemValue);
        BeanUtils.copyProperties(proFormaInvoiceItemEntity.getGlassThicknessEntity(), proFormaInvoiceItemValue);
        BeanUtils.copyProperties(proFormaInvoiceItemEntity, proFormaInvoiceItemValue);
        proFormaInvoiceItemValue.setTenantUuid(tenantUuid);
        proFormaInvoiceItemValue.setProFormaInvoiceUuid(proFormaInvoiceItemValue.getProFormaInvoiceUuid());*/
        return proFormaInvoiceItemRepository.findByUuid(proFormaInvoiceItemUuid).toDTO();
    }

    @Override
    public int deleteProFormInvoiceItem(String tenantUuid,String ProFormaInvoiceItemUuid) throws Exception {

      return   proFormaInvoiceItemRepository.deleteByProFormaInvoiceItemUuid(ProFormaInvoiceItemUuid);

    }

    @Override
    public List<ProFormaInvoiceItemValue> getAllProFormInvoiceItem(String proFormInvoiceUuid) throws Exception {
        List<ProFormaInvoiceItemValue> proFormaInvoiceItemValues = new ArrayList<>();
        ProFormaInvoiceItemValue proFormaInvoiceItemValue = null;
        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities = proFormaInvoiceItemRepository.findAllByProFormaInvoiceEntity_proFormInvoiceUuid(proFormInvoiceUuid);
        return proFormaInvoiceItemEntities.stream().map(piie -> piie.toDTO()).collect(Collectors.toList());
/*        for (int i = 0; i < proFormaInvoiceItemEntities.size(); i++) {
            proFormaInvoiceItemValue = new ProFormaInvoiceItemValue();
            BeanUtils.copyProperties(proFormaInvoiceItemEntities.get(i).getGlassTypeEntity(), proFormaInvoiceItemValue);
            BeanUtils.copyProperties(proFormaInvoiceItemEntities.get(i).getGlassSpecificationEntity(), proFormaInvoiceItemValue);
            BeanUtils.copyProperties(proFormaInvoiceItemEntities.get(i).getGlassThicknessEntity(), proFormaInvoiceItemValue);
            BeanUtils.copyProperties(proFormaInvoiceItemEntities.get(i), proFormaInvoiceItemValue);

            proFormaInvoiceItemValue.setTenantUuid(tenantUuid);
            proFormaInvoiceItemValue.setGlassTypeUuid(proFormaInvoiceItemValue.getGlassTypeUuid());
            proFormaInvoiceItemValue.setGlassSpecificationUuid(proFormaInvoiceItemValue.getGlassSpecificationUuid());
            proFormaInvoiceItemValue.setGlassThicknessUuid(proFormaInvoiceItemValue.getGlassThicknessUuid());
            proFormaInvoiceItemValues.add(proFormaInvoiceItemValue);
        }
        return proFormaInvoiceItemValues;*/
    }
}

