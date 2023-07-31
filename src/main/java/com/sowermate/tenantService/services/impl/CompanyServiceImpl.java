package com.sowermate.tenantService.services.impl;
import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.repositories.AddressRepository;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.CompanyTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName= {"Exception"})
public class  CompanyServiceImpl  implements CompanyService {
    @Autowired
    private CompanyRepository  companyRepository;
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;
    @Override
    public CompanyValue createCompany(CompanyValue companyValue) throws Exception {
        CompanyEntity companyEntity=new CompanyEntity();
        BeanUtils.copyProperties(companyValue ,companyEntity);
        String randomCompanyUuid= UUID.randomUUID().toString();
        companyEntity.setCompanyUuid(randomCompanyUuid);
        companyEntity.setTenantEntity(tenantRepository.findByTenantUuid(companyValue.getTenantUuid()));
        companyEntity.setCompanyTypeEntity(companyTypeRepository.findByCompanyTypeUuid(companyValue.getCompanyTypeUuid()));
        BeanUtils.copyProperties(companyRepository.save(companyEntity), companyValue);
        return companyValue;
    }

    @Override
    public CompanyValue editCompany(CompanyValue companyValue) throws Exception {
         CompanyEntity  companyEntity=new CompanyEntity();
         BeanUtils.copyProperties(companyValue , companyEntity);
        companyEntity.setTenantEntity(tenantRepository.findByTenantUuid(companyValue.getTenantUuid()));
        //companyEntity.setCompanyTypeEntity(companyTypeRepository.findByCompanyTypeUuid(companyValue.getCompanyTypeUuid()));
         companyEntity.setCompanyId(companyRepository.findByTenantEntity_UuidAndCompanyUuid( companyValue.getTenantUuid(),companyValue.getCompanyUuid()).getCompanyId());
         BeanUtils.copyProperties(companyRepository.save(companyEntity), companyValue);
        return companyValue;
    }
    @Override
    public CompanyValue getCompany(String tenantUuid,String companyUuid) throws Exception {
        CompanyValue companyValue =new CompanyValue();
        CompanyEntity companyEntity=companyRepository.findByTenantEntity_UuidAndCompanyUuid(tenantUuid,companyUuid);
        BeanUtils.copyProperties(companyEntity.getCompanyTypeEntity(), companyValue);
        BeanUtils.copyProperties(companyEntity, companyValue);
        companyValue.setTenantUuid(tenantUuid);
        companyValue.setCompanyTypeUuid(companyValue.getCompanyTypeUuid());
        return companyValue;
    }
    
    @Override
    public CompanyValue deleteCompany(String tenantUuid,String companyUuid) throws Exception {
        CompanyValue companyValue = new CompanyValue();
        companyRepository.softDelete(companyUuid);
        CompanyEntity companyEntity= companyRepository.findByTenantEntity_UuidAndCompanyUuid(tenantUuid,companyUuid);
        BeanUtils.copyProperties(companyEntity, companyValue);
        return companyValue;
    }

    @Override
    public List<CompanyValue> getAllCompany(String tenantUuid ) throws Exception {
        List<CompanyValue> companyValues = new ArrayList<>();

        CompanyValue companyValue = null;
        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(tenantUuid);
        List<CompanyEntity> companyEntities = companyRepository.findAllByTenantEntityTenantId(tenantEntity.getTenantId());
        for (int i = 0; i < companyEntities.size(); i++) {
           companyValue = new CompanyValue();
            //BeanUtils.copyProperties(companyEntities.get(i).getCompanyTypeEntity(), companyValue);
            BeanUtils.copyProperties(companyEntities.get(i), companyValue);
            companyValue.setTenantUuid(tenantUuid);
            companyValue.setCompanyTypeUuid(companyValue.getCompanyTypeUuid());

            companyValues.add(companyValue);
        }
        return companyValues;
    }
}


