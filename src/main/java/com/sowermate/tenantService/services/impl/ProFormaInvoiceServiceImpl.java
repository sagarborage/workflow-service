package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.ProFormInvoiceValue;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.ConfirmThroughRepository;
import com.sowermate.tenantService.repositories.PiTypeRepository;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
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


    @Override
    public ProFormInvoiceValue createProFormInvoice(ProFormInvoiceValue proFormInvoiceValue) throws Exception {
        ProFormaInvoiceEntity proFormaInvoiceEntity=new ProFormaInvoiceEntity();
        BeanUtils.copyProperties(proFormInvoiceValue ,proFormaInvoiceEntity);
        String randomProFormaInvoiceUuid= UUID.randomUUID().toString();
        proFormaInvoiceEntity.setProFormInvoiceUuid(randomProFormaInvoiceUuid);
        proFormaInvoiceEntity.setConfirmThroughEntity(confirmThroughRepository.findByConfirmThroughUuid(proFormInvoiceValue.getConfirmThroughUuid()));
        proFormaInvoiceEntity.setPiTypeEntity(piTypeRepository.findByPiTypeUuid(proFormInvoiceValue.getPiTypeUuid()));
        proFormaInvoiceEntity.setCompanyIdBill(companyRepository.findByUuid(proFormInvoiceValue.getIdBillToUuid()));
        proFormaInvoiceEntity.setCompanyIdShip(companyRepository.findByUuid(proFormInvoiceValue.getIdShipToUuid()));
        BeanUtils.copyProperties(proFormaInvoiceRepository.save(proFormaInvoiceEntity), proFormInvoiceValue);
        return proFormInvoiceValue;
    }

    @Override
    public ProFormInvoiceValue editProFormInvoice(ProFormInvoiceValue proFormInvoiceValue) throws Exception {
        ProFormaInvoiceEntity proFormaInvoiceEntity=new ProFormaInvoiceEntity();
        BeanUtils.copyProperties(proFormInvoiceValue , proFormaInvoiceEntity);
        proFormaInvoiceEntity.setConfirmThroughEntity(confirmThroughRepository.findByConfirmThroughUuid(proFormInvoiceValue.getConfirmThroughUuid()));
        proFormaInvoiceEntity.setPiTypeEntity(piTypeRepository.findByPiTypeUuid(proFormInvoiceValue.getPiTypeUuid()));
        proFormaInvoiceEntity.setCompanyIdBill(companyRepository.findByUuid(proFormInvoiceValue.getIdBillToUuid()));
        proFormaInvoiceEntity.setCompanyIdShip(companyRepository.findByUuid(proFormInvoiceValue.getIdShipToUuid()));
        proFormaInvoiceEntity.setProFormaInvoiceId(proFormaInvoiceRepository.findByProFormaInvoiceUuid(proFormInvoiceValue.getProFormInvoiceUuid()).getProFormaInvoiceId());
        BeanUtils.copyProperties(proFormaInvoiceRepository.save(proFormaInvoiceEntity), proFormInvoiceValue);
        return proFormInvoiceValue;
    }
    @Override
    public ProFormInvoiceValue getProFormInvoice(String proFormInvoiceUuid) throws Exception {
        ProFormInvoiceValue proFormInvoiceValue =new ProFormInvoiceValue();
        ProFormaInvoiceEntity proFormaInvoiceEntity=proFormaInvoiceRepository.findByProFormaInvoiceUuid(proFormInvoiceUuid);
        BeanUtils.copyProperties(proFormaInvoiceEntity, proFormInvoiceValue);
        return proFormInvoiceValue;
    }

    @Override
    public ProFormInvoiceValue deleteProFormInvoice(String proFormInvoiceUuid) throws Exception {
        ProFormInvoiceValue proFormInvoiceValue=new ProFormInvoiceValue();
        proFormaInvoiceRepository.deleteByProFormaInvoiceUuid(proFormInvoiceUuid);
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByProFormaInvoiceUuid(proFormInvoiceUuid);
        BeanUtils.copyProperties(proFormaInvoiceEntity, proFormInvoiceValue);
        return proFormInvoiceValue;

    }

    @Override
    public List<ProFormInvoiceValue> getAllProFormInvoice() throws Exception {
        List<ProFormInvoiceValue> proFormInvoiceValues = new ArrayList<>();
        ProFormInvoiceValue proFormInvoiceValue = null;
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = proFormaInvoiceRepository.findAll();
        for (int i = 0; i < proFormaInvoiceEntities.size(); i++) {
            proFormInvoiceValue = new ProFormInvoiceValue();
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i), proFormInvoiceValue);
            proFormInvoiceValues.add(proFormInvoiceValue);
        }
        return proFormInvoiceValues;
    }
}
