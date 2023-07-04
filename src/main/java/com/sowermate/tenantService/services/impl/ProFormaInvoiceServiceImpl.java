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
        proFormaInvoiceEntity.setTenantEntity(tenantRepository.findByTenantUuid(proFormInvoiceValue.getTenantUuid()));
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
        proFormaInvoiceEntity.setTenantEntity(tenantRepository.findByTenantUuid(proFormInvoiceValue.getTenantUuid()));
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
        BeanUtils.copyProperties(proFormaInvoiceEntity.getConfirmThroughEntity(), proFormInvoiceValue);
        BeanUtils.copyProperties(proFormaInvoiceEntity.getPiTypeEntity(), proFormInvoiceValue);
        BeanUtils.copyProperties(proFormaInvoiceEntity, proFormInvoiceValue);
        proFormInvoiceValue.setTenantUuid(tenantUuid);

        return proFormInvoiceValue;
    }

    @Override
    public int deleteProFormInvoice(String tenantUuid,String proFormInvoiceUuid) throws Exception {

     return    proFormaInvoiceRepository.deleteByProFormaInvoiceUuid(proFormInvoiceUuid);



    }

    @Override
    public List<ProFormInvoiceValue> getAllProFormInvoice(String tenantUuid) throws Exception {
        List<ProFormInvoiceValue> proFormInvoiceValues = new ArrayList<>();
        ProFormInvoiceValue proFormInvoiceValue = null;
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = proFormaInvoiceRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i = 0; i < proFormaInvoiceEntities.size(); i++) {
            proFormInvoiceValue = new ProFormInvoiceValue();
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i).getConfirmThroughEntity(), proFormInvoiceValue);
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i).getPiTypeEntity(), proFormInvoiceValue);
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i).getCompanyIdShip().getCompanyId(), proFormInvoiceValue);
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i).getCompanyIdBill().getCompanyId() , proFormInvoiceValue);
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i), proFormInvoiceValue);
            proFormInvoiceValue.setTenantUuid(tenantUuid);
            proFormInvoiceValue.setConfirmThroughUuid(proFormInvoiceValue.getConfirmThroughUuid());
            proFormInvoiceValue.setPiTypeUuid(proFormInvoiceValue.getPiTypeUuid());
            proFormInvoiceValue.setIdBillToUuid(proFormInvoiceValue.getIdBillToUuid());
            proFormInvoiceValue.setIdShipToUuid(proFormInvoiceValue.getIdShipToUuid());
            proFormInvoiceValues.add(proFormInvoiceValue);
        }
        return proFormInvoiceValues;
    }
}
