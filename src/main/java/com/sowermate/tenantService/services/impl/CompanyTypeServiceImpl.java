package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.CompanyTypeEntity;
import com.sowermate.tenantService.entities.value.CompanyTypeValue;
import com.sowermate.tenantService.repositories.CompanyTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.CompanyTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class CompanyTypeServiceImpl implements CompanyTypeService {

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public CompanyTypeValue createCompanyType(CompanyTypeValue companyTypeValue) throws Exception {
        CompanyTypeEntity companyTypeEntity = new CompanyTypeEntity();
        BeanUtils.copyProperties(companyTypeValue, companyTypeEntity);
        String randomTenantUuid = UUID.randomUUID().toString();
        companyTypeEntity.setCompanyTypeUuid(randomTenantUuid);
        companyTypeEntity.setTenantEntity(tenantRepository.findByTenantUuid(companyTypeValue.getTenantUuid()));
        BeanUtils.copyProperties(companyTypeRepository.save(companyTypeEntity), companyTypeValue);
        return companyTypeValue;
    }


    @Override
    public CompanyTypeValue editCompanyType(CompanyTypeValue companyTypeValue) throws Exception {
        CompanyTypeEntity companyTypeEntity = new CompanyTypeEntity();
        BeanUtils.copyProperties(companyTypeValue, companyTypeEntity);

        //Check that UUID is not null before searching for the tenant
        if (companyTypeValue.getCompanyTypeUuid() != null) {
            CompanyTypeEntity matchingCompanyTypes = companyTypeRepository.findByTenantEntity_UuidAndCompanyTypeUuid(companyTypeValue.getTenantUuid(), companyTypeValue.getCompanyTypeUuid());
            if (matchingCompanyTypes != null) {
                companyTypeEntity.setCompanyTypeId(matchingCompanyTypes.getCompanyTypeId());
                companyTypeEntity.setTenantEntity(tenantRepository.findByTenantUuid(companyTypeValue.getTenantUuid()));
                BeanUtils.copyProperties(companyTypeRepository.save(companyTypeEntity), companyTypeValue);
            } else {
                throw new Exception("No tenant found with UUID " + companyTypeValue.getCompanyTypeUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return companyTypeValue;
    }

    @Override
    public List<CompanyTypeValue>  getAllCompanyType(String tenantUuid) throws Exception {
        List<CompanyTypeValue> companyTypeValues = new ArrayList<>();
        CompanyTypeValue companyTypeValue = null;
        List<CompanyTypeEntity> companyTypeEntities = companyTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i = 0; i < companyTypeEntities.size(); i++) {
            companyTypeValue = new CompanyTypeValue();
            BeanUtils.copyProperties(companyTypeEntities.get(i), companyTypeValue);
            companyTypeValue.setTenantUuid(tenantUuid);
            companyTypeValues.add(companyTypeValue);
        }

        return companyTypeValues;
    }

    @Override
    public CompanyTypeValue getCompanyType(String tenantUuid, String companyTypeUuid) throws Exception {
        CompanyTypeValue companyTypeValue = new CompanyTypeValue();

        CompanyTypeEntity companyTypeEntity = companyTypeRepository.findByTenantEntity_UuidAndCompanyTypeUuid(tenantUuid, companyTypeUuid);
        BeanUtils.copyProperties(companyTypeEntity, companyTypeValue);
        companyTypeValue.setTenantUuid(tenantUuid);
        return companyTypeValue;
    }

    @Override
    public CompanyTypeValue deleteCompanyType(String tenantUuid, String companyTypeUuid) throws Exception {
        CompanyTypeValue companyTypeValue = new CompanyTypeValue();
        companyTypeRepository.softDelete(companyTypeUuid);
        CompanyTypeEntity companyTypeEntity = companyTypeRepository.findByTenantEntity_UuidAndCompanyTypeUuid(tenantUuid, companyTypeUuid);
        BeanUtils.copyProperties(companyTypeEntity, companyTypeValue);
        return companyTypeValue;
    }

}
