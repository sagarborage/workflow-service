package com.sowermate.tenantService.services.impl;
import com.sowermate.tenantService.entities.AddressEntity;
import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.repositories.AddressRepository;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.CompanyTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.CommonService;
import com.sowermate.tenantService.services.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackForClassName= {"Exception"})
public class  CompanyServiceImpl extends CommonService implements CompanyService {
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
        initCreate(companyEntity);
        companyEntity.setTenantDetailsEntity(tenantRepository.findByUuid(companyValue.getTenantUUID()).get(0));
        companyEntity.setAddressEntity(addressRepository.findByUuid(companyValue.getAddressUUID()).get(0));
        companyEntity.setCompanyTypeEntity(companyTypeRepository.findByUuid(companyValue.getCompanyTypeUUID()).get(0));
        BeanUtils.copyProperties(companyRepository.save(companyEntity), companyValue);
        return companyValue;
    }
    @Override
    public CompanyValue editCompany(CompanyValue companyValue) throws Exception {
         CompanyEntity  companyEntity=new CompanyEntity();
         BeanUtils.copyProperties(companyValue , companyEntity);
         initEdit(companyEntity);
        companyEntity.setTenantDetailsEntity(tenantRepository.findByUuid(companyValue.getTenantUUID()).get(0));
        companyEntity.setAddressEntity(addressRepository.findByUuid(companyValue.getAddressUUID()).get(0));
        companyEntity.setCompanyTypeEntity(companyTypeRepository.findByUuid(companyValue.getCompanyTypeUUID()).get(0));
         companyEntity.setCompanyId(companyRepository.findByUuid(companyValue.getUuid()).get(0).getCompanyId());
         BeanUtils.copyProperties(companyRepository.save(companyEntity), companyValue);
        return companyValue;
    }
    @Override
    public CompanyValue getCompany(String uuid) throws Exception {
        CompanyValue companyValue =new CompanyValue();
        CompanyEntity companyEntity=companyRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(companyEntity, companyValue);
        return companyValue;
    }
    
    @Override
    public CompanyValue deleteCompany(String uuid) throws Exception {
        CompanyValue companyValue = new CompanyValue();
        companyRepository.softDelete(uuid);
        CompanyEntity companyEntity= companyRepository.findByUuid(uuid).get(0);
        BeanUtils.copyProperties(companyEntity, companyValue);
        return companyValue;
    }

    @Override
    public List<CompanyValue> getAllCompany() throws Exception {
        List<CompanyValue> companyValues = new ArrayList<>();
        CompanyValue companyValue = null;
        List<CompanyEntity> companyEntities = companyRepository.findAll();
        for (int i = 0; i < companyEntities.size(); i++) {
            companyValue = new CompanyValue();
            BeanUtils.copyProperties(companyEntities.get(i), companyValue);
            companyValues.add(companyValue);
        }
        return companyValues;
    }
}


