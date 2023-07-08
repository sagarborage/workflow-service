package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.AddressTypeEntity;
import com.sowermate.tenantService.entities.CompanyAddressEntity;
import com.sowermate.tenantService.entities.GlassSpecificationEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import com.sowermate.tenantService.entities.value.CompanyAddressValue;
import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.CompanyAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName= {"Exception"})
public class CompanyAddressServiceImpl implements CompanyAddressService {

    @Autowired
    private CompanyAddressRepository companyAddressRepository;
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired

    private AddressTypeRepository addressTypeRepository;

    @Override
    public CompanyAddressValue createCompanyAddress(CompanyAddressValue companyAddressValue) throws Exception {
        CompanyAddressEntity companyAddressEntity = new CompanyAddressEntity();
        BeanUtils.copyProperties(companyAddressValue, companyAddressEntity);
        String randomCompanyAddressUuid = UUID.randomUUID().toString();
        companyAddressEntity.setCompanyAddressUuid(randomCompanyAddressUuid);
        companyAddressEntity.setTenantEntity(tenantRepository.findByTenantUuid(companyAddressValue.getTenantUuid()));
        companyAddressEntity.setAddressTypeEntity(addressTypeRepository.findByAddressTypeUuid(companyAddressValue.getAddressTypeUuid()));
        companyAddressEntity.setCompanyEntity(companyRepository.findByTenantEntity_UuidAndCompanyUuid(companyAddressValue.getTenantUuid(), companyAddressValue.getCompanyUuid()));
        companyAddressEntity.setAddressEntity(addressRepository.findByTenantEntity_UuidAndAddressUuid(companyAddressValue.getTenantUuid(), companyAddressValue.getAddressUuid()));
        BeanUtils.copyProperties(companyAddressRepository.save(companyAddressEntity), companyAddressValue);
        return companyAddressValue;
    }

    @Override
    public CompanyAddressValue editCompanyAddress(CompanyAddressValue companyAddressValue) throws Exception {
        CompanyAddressEntity companyAddressEntity = new CompanyAddressEntity();
        BeanUtils.copyProperties(companyAddressValue, companyAddressEntity);
        companyAddressEntity.setCompanyAddressId(companyAddressRepository.findByTenantEntity_UuidAndCompanyAddressUuid(companyAddressValue.getTenantUuid(), companyAddressValue.getCompanyAddressUuid()).getCompanyAddressId());
        companyAddressEntity.setTenantEntity(tenantRepository.findByTenantUuid(companyAddressValue.getTenantUuid()));
        companyAddressEntity.setAddressTypeEntity(addressTypeRepository.findByAddressTypeUuid(companyAddressValue.getAddressTypeUuid()));
        companyAddressEntity.setCompanyEntity(companyRepository.findByTenantEntity_UuidAndCompanyUuid(companyAddressValue.getTenantUuid(), companyAddressValue.getCompanyUuid()));
        companyAddressEntity.setAddressEntity(addressRepository.findByTenantEntity_UuidAndAddressUuid(companyAddressValue.getTenantUuid(), companyAddressValue.getAddressUuid()));
        BeanUtils.copyProperties(companyAddressRepository.save(companyAddressEntity), companyAddressValue);
        return companyAddressValue;
    }

    @Override
    public CompanyAddressValue getCompanyAddress(String tenantUuid, String companyAddressUuid) throws Exception {
        CompanyAddressValue companyAddressValue = new CompanyAddressValue();
        CompanyAddressEntity companyAddressEntity = companyAddressRepository.findByTenantEntity_UuidAndCompanyAddressUuid(tenantUuid, companyAddressUuid);
        BeanUtils.copyProperties(companyAddressEntity.getCompanyEntity(), companyAddressValue);
        BeanUtils.copyProperties(companyAddressEntity.getAddressEntity(), companyAddressValue);
        BeanUtils.copyProperties(companyAddressEntity.getAddressTypeEntity(), companyAddressValue);
        BeanUtils.copyProperties(companyAddressEntity, companyAddressValue);
        companyAddressValue.setTenantUuid(tenantUuid);
        companyAddressValue.setCompanyUuid(companyAddressValue.getCompanyUuid());
        return companyAddressValue;
    }

    @Override
    public List<CompanyAddressValue> getAllCompanyAddress(String tenantUuid) throws Exception {
        List<CompanyAddressValue> companyAddressValues = new ArrayList<>();
        CompanyAddressValue companyAddressValue = null;
        List<CompanyAddressEntity> companyAddressEntities = companyAddressRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i = 0; i < companyAddressEntities.size(); i++) {
            companyAddressValue = new CompanyAddressValue();
            BeanUtils.copyProperties(companyAddressEntities.get(i).getCompanyEntity(), companyAddressValue);
            BeanUtils.copyProperties(companyAddressEntities.get(i).getAddressEntity(), companyAddressValue);
            BeanUtils.copyProperties(companyAddressEntities.get(i).getAddressTypeEntity(), companyAddressValue);
            BeanUtils.copyProperties(companyAddressEntities.get(i), companyAddressValue);
            companyAddressValue.setTenantUuid(tenantUuid);
            companyAddressValue.setCompanyUuid(companyAddressValue.getCompanyUuid());
            companyAddressValue.setAddressUuid(companyAddressValue.getAddressUuid());
            companyAddressValue.setAddressTypeUuid(companyAddressValue.getAddressTypeUuid());
            companyAddressValues.add(companyAddressValue);
        }
        return companyAddressValues;
    }

    @Override
    public int deleteCompanyAddress(String tenantUuid, String companyAddressUuid) throws Exception {
        return  companyAddressRepository.softDelete(companyAddressUuid);
        /*  CompanyAddressValue companyAddressValue = new CompanyAddressValue();
       CompanyAddressEntity companyAddressEntity = companyAddressRepository.findByTenantEntity_UuidAndCompanyAddressUuid(tenantUuid, companyAddressUuid);
       BeanUtils.copyProperties(companyAddressEntity, companyAddressValue);
        return companyAddressValue;*/
    }
}