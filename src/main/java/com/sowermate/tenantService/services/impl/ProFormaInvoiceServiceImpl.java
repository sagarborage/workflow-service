package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
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
    public ProFormaInvoiceValue createProFormInvoice(ProFormaInvoiceValue proFormInvoiceValue) throws Exception {
/*        ProFormaInvoiceEntity proFormaInvoiceEntity=new ProFormaInvoiceEntity();
        BeanUtils.copyProperties(proFormInvoiceValue ,proFormaInvoiceEntity);
        proFormaInvoiceEntity.setProFormInvoiceUuid(CommonUtils.generateUUID());
        proFormaInvoiceEntity.setTenantEntity(tenantRepository.findByTenantUuid(proFormInvoiceValue.getTenantUuid()));
        proFormaInvoiceEntity.setConfirmThroughEntity(confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getConfirmThroughUuid()));
        proFormaInvoiceEntity.setPiTypeEntity(piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getPiTypeUuid()));
        proFormaInvoiceEntity.setCompanyIdBill(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getIdBillToUuid()));
        proFormaInvoiceEntity.setCompanyIdShip(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(proFormInvoiceValue.getTenantUuid(),proFormInvoiceValue.getIdShipToUuid()));
        BeanUtils.copyProperties(proFormaInvoiceRepository.save(proFormaInvoiceEntity), proFormInvoiceValue);
        return proFormInvoiceValue;*/
        String tenantUUID = proFormInvoiceValue.getTenantValue().getUuid();
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormInvoiceValue.toEntity().toBuilder()
                .proFormInvoiceUuid(CommonUtils.generateUUID())
                .tenantEntity(tenantRepository.findByTenantUuid(proFormInvoiceValue.getTenantValue().getUuid()))
                .confirmThroughEntity(confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUUID, proFormInvoiceValue.getProFormInvoiceUuid()))
                .piTypeEntity(piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(tenantUUID, proFormInvoiceValue.getPiTypeValue().getPiTypeUuid()))
                .companyIdBill(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormInvoiceValue.getCompanyIdBill().getCompanyUuid()))
                .companyIdShip(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormInvoiceValue.getCompanyIdShip().getCompanyUuid()))
                .build();
        return proFormaInvoiceEntity.toDTO();
    }

    @Override
    public ProFormaInvoiceValue editProFormInvoice(ProFormaInvoiceValue proFormInvoiceValue) throws Exception {

        String tenantUUID = proFormInvoiceValue.getTenantValue().getUuid();
        ProFormaInvoiceEntity tempProFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(proFormInvoiceValue.getTenantValue().getUuid(),
                proFormInvoiceValue.getProFormInvoiceUuid());

        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormInvoiceValue.toEntity().toBuilder()
                .proFormaInvoiceId(tempProFormaInvoiceEntity.getProFormaInvoiceId())
                .tenantEntity(tenantRepository.findByTenantUuid(proFormInvoiceValue.getTenantValue().getUuid()))
                .confirmThroughEntity(confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUUID, proFormInvoiceValue.getProFormInvoiceUuid()))
                .piTypeEntity(piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(tenantUUID, proFormInvoiceValue.getPiTypeValue().getPiTypeUuid()))
                .companyIdBill(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormInvoiceValue.getCompanyIdBill().getCompanyUuid()))
                .companyIdShip(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormInvoiceValue.getCompanyIdShip().getCompanyUuid()))
                .build();
        return proFormaInvoiceEntity.toDTO();
    }

    @Override
    public ProFormaInvoiceValue getProFormInvoice(String tenantUuid, String proFormInvoiceUuid) throws Exception {
/*        ProFormaInvoiceValue proFormInvoiceValue = new ProFormaInvoiceValue();
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(tenantUuid, proFormInvoiceUuid);
        BeanUtils.copyProperties(proFormaInvoiceEntity.getConfirmThroughEntity(), proFormInvoiceValue);
        BeanUtils.copyProperties(proFormaInvoiceEntity.getPiTypeEntity(), proFormInvoiceValue);
        BeanUtils.copyProperties(proFormaInvoiceEntity, proFormInvoiceValue);
        proFormInvoiceValue.setTenantUuid(tenantUuid);*/

        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(tenantUuid, proFormInvoiceUuid);
        return proFormaInvoiceEntity.toDTO();
    }

    @Override
    public int deleteProFormInvoice(String tenantUuid, String proFormInvoiceUuid) throws Exception {
        return proFormaInvoiceRepository.deleteByProFormaInvoiceUuid(proFormInvoiceUuid);
    }

    @Override
    public List<ProFormaInvoiceValue> getAllProFormInvoice(String tenantUuid) throws Exception {
/*        List<ProFormaInvoiceValue> proFormInvoiceValues = new ArrayList<>();
        ProFormaInvoiceValue proFormInvoiceValue = null;
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = proFormaInvoiceRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i = 0; i < proFormaInvoiceEntities.size(); i++) {
            proFormInvoiceValue = new ProFormaInvoiceValue();
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i).getConfirmThroughEntity(), proFormInvoiceValue);
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i).getPiTypeEntity(), proFormInvoiceValue);
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i).getCompanyIdShip().getCompanyId(), proFormInvoiceValue);
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i).getCompanyIdBill().getCompanyId(), proFormInvoiceValue);
            BeanUtils.copyProperties(proFormaInvoiceEntities.get(i), proFormInvoiceValue);
            proFormInvoiceValue.setTenantUuid(tenantUuid);
            proFormInvoiceValue.setConfirmThroughUuid(proFormInvoiceValue.getConfirmThroughUuid());
            proFormInvoiceValue.setPiTypeUuid(proFormInvoiceValue.getPiTypeUuid());
            proFormInvoiceValue.setIdBillToUuid(proFormInvoiceValue.getIdBillToUuid());
            proFormInvoiceValue.setIdShipToUuid(proFormInvoiceValue.getIdShipToUuid());
            proFormInvoiceValues.add(proFormInvoiceValue);
        }*/
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = proFormaInvoiceRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return proFormaInvoiceEntities.stream().map(pie -> pie.toDTO()).collect(Collectors.toList());
    }
}
