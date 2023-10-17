package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.CompanyTypeEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.CompanyTypeValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.CompanyTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.CompanyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class CompanyTypeServiceImpl implements CompanyTypeService {

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private TenantRepository tenantRepository;


    @Override
    public CompanyTypeValue createCompanyType(CompanyTypeValue companyTypeValue) {
        TenantEntity tenantEntity =getTenantEntity(companyTypeValue.getTenantUuid());
        CompanyTypeEntity companyTypeEntity = companyTypeValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .build();
        return companyTypeRepository.save(companyTypeEntity).toDTO();
    }


    @Override
    public CompanyTypeValue editCompanyType(CompanyTypeValue companyTypeValue) {
        TenantEntity tenantEntity = getTenantEntity(companyTypeValue.getTenantUuid());
        CompanyTypeEntity companyTypeTemp = getCompanyTypeEntity(companyTypeValue.getUuid());

        CompanyTypeEntity companyTypeEntity = companyTypeValue.toEntity().toBuilder()
                .id(companyTypeTemp.getId())
                .tenantEntity(tenantEntity)
                .createdDateTime(companyTypeTemp.getCreatedDateTime())
                .createdBy(companyTypeTemp.getCreatedBy())
                .build();

        return companyTypeRepository.save(companyTypeEntity).toDTO();
    }

    @Override
    public List<CompanyTypeValue> getAllCompanyType(String tenantUuid) {
        List<CompanyTypeEntity> companyTypeEntities = companyTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return companyTypeEntities.stream().map(e -> e.toDTO()).collect(Collectors.toList());
    }

    @Override
    public CompanyTypeValue getCompanyType(String tenantUuid, String companyTypeUuid) {
        return getCompanyTypeEntity(companyTypeUuid).toDTO();
    }

    @Override
    public CompanyTypeValue deleteCompanyType(String tenantUuid, String companyTypeUuid) {
        CompanyTypeEntity companyTypeEntity = getCompanyTypeEntity(companyTypeUuid);
        companyTypeRepository.softDelete(companyTypeUuid);
        return null;
    }

    public TenantEntity getTenantEntity(String tenantUuid){
        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUuid);
        if(tenantEntity==null){
            throw new ResourceNotFoundException("TenantEntity","tenantUuid",tenantUuid);
        }
        return tenantEntity;
    }

    public CompanyTypeEntity getCompanyTypeEntity(String companyTypeUuid) {
        CompanyTypeEntity companyTypeEntity =  companyTypeRepository.findByUuid(companyTypeUuid);
        if(companyTypeEntity==null){
            throw new ResourceNotFoundException("companyTypeEntity","companyTypeUuid",companyTypeUuid);
        }
        return companyTypeEntity;
    }
}
