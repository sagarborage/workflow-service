package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.ProFormInvoiceValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName= {"Exception"})
public class ProFormaInvoiceServiceImpl implements ProFormaInvoiceService {

    @Autowired
     private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
     private ConfirmThroughRepository confirmThroughRepository;

    @Autowired
     private PiTypeRepository piTypeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TenantRepository tenantRepository;


    @Override
    public ProFormInvoiceValue createProFormInvoice(ProFormInvoiceValue proFormInvoiceValue) throws Exception {
        ProFormaInvoiceEntity proFormaInvoiceEntity=new ProFormaInvoiceEntity();
        BeanUtils.copyProperties(proFormInvoiceValue ,proFormaInvoiceEntity);
        String randomProFormaInvoiceUuid= UUID.randomUUID().toString();
        proFormaInvoiceEntity.setProFormInvoiceUuid(randomProFormaInvoiceUuid);
        proFormaInvoiceEntity.setConfirmThroughEntity(confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getConfirmThroughUuid()));
        proFormaInvoiceEntity.setPiTypeEntity(piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getPiTypeUuid()));
        proFormaInvoiceEntity.setCompanyIdBill(companyRepository.findByTenantEntity_UuidAndCompanyUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getIdBillToUuid()));
        proFormaInvoiceEntity.setCompanyIdShip(companyRepository.findByTenantEntity_UuidAndCompanyUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getIdShipToUuid()));
        BeanUtils.copyProperties(proFormaInvoiceRepository.save(proFormaInvoiceEntity), proFormInvoiceValue);
        return proFormInvoiceValue;
    }

    @Override
    public ProFormInvoiceValue editProFormInvoice(ProFormInvoiceValue proFormInvoiceValue) throws Exception {
        ProFormaInvoiceEntity proFormaInvoiceEntity=new ProFormaInvoiceEntity();
        BeanUtils.copyProperties(proFormInvoiceValue , proFormaInvoiceEntity);
        proFormaInvoiceEntity.setConfirmThroughEntity(confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getConfirmThroughUuid()));
        proFormaInvoiceEntity.setPiTypeEntity(piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getPiTypeUuid()));
        proFormaInvoiceEntity.setCompanyIdBill(companyRepository.findByTenantEntity_UuidAndCompanyUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getIdBillToUuid()));
        proFormaInvoiceEntity.setCompanyIdShip(companyRepository.findByTenantEntity_UuidAndCompanyUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getIdShipToUuid()));
        proFormaInvoiceEntity.setProFormaInvoiceId(proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getProFormInvoiceUuid()).getProFormaInvoiceId());
        BeanUtils.copyProperties(proFormaInvoiceRepository.save(proFormaInvoiceEntity), proFormInvoiceValue);
        return proFormInvoiceValue;
    }
    @Override
    public ProFormInvoiceValue getProFormInvoice(String tenantUuid,String proFormInvoiceUuid) throws Exception {
        ProFormInvoiceValue proFormInvoiceValue =new ProFormInvoiceValue();
        ProFormaInvoiceEntity proFormaInvoiceEntity=proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(tenantUuid,proFormInvoiceUuid);
        BeanUtils.copyProperties(proFormaInvoiceEntity, proFormInvoiceValue);
        return proFormInvoiceValue;
    }

    @Override
    public ProFormInvoiceValue deleteProFormInvoice(String tenantUuid,String proFormInvoiceUuid) throws Exception {
        ProFormInvoiceValue proFormInvoiceValue=new ProFormInvoiceValue();
        proFormaInvoiceRepository.deleteByProFormaInvoiceUuid(proFormInvoiceUuid);
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(tenantUuid,proFormInvoiceUuid);
        BeanUtils.copyProperties(proFormaInvoiceEntity, proFormInvoiceValue);
        return proFormInvoiceValue;

    }

    @Override
    public List<ProFormInvoiceValue> getAllProFormInvoice(String tenantUuid) throws Exception {
        List<ProFormInvoiceValue> proFormInvoiceValues = new ArrayList<>();
        ProFormInvoiceValue proFormInvoiceValue = null;
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = proFormaInvoiceRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i = 0; i < proFormaInvoiceEntities.size(); i++) {
            proFormInvoiceValue = new ProFormInvoiceValue();
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i), proFormInvoiceValue);
            proFormInvoiceValues.add(proFormInvoiceValue);
        }
        return proFormInvoiceValues;
    }
}
