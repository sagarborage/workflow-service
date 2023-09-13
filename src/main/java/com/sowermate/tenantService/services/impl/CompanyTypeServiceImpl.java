package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.CompanyTypeEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.CompanyTypeValue;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.CompanyTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.CompanyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class CompanyTypeServiceImpl implements CompanyTypeService {

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public CompanyTypeValue createCompanyType(CompanyTypeValue companyTypeValue) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(companyTypeValue.getTenantValue().getUuid());
        CompanyEntity companyEntity = companyRepository.getCompanyEntityByUuid(companyTypeValue.getCompanyValue().getCompanyUuid());
        CompanyTypeEntity companyTypeEntity = companyTypeValue.toEntity().toBuilder()
                //.companyTypeUuid(CommonUtils.generateUUID())
                //.tenantEntity(tenantEntity)
                .companyEntiies(Arrays.asList(companyEntity))
                .build();
        //CompanyTypeEntity companyTypeEntity = new CompanyTypeEntity();
        //BeanUtils.copyProperties(companyTypeValue, companyTypeEntity);
        //companyTypeEntity.setCompanyTypeUuid(CommonUtils.generateUUID());
        //companyTypeEntity.setTenantEntity(tenantRepository.findByTenantUuid(companyTypeValue.getTenantUuid()));
        //BeanUtils.copyProperties(companyTypeRepository.save(companyTypeEntity), companyTypeValue);
        return companyTypeRepository.save(companyTypeEntity).toDTO();
    }


    @Override
    public CompanyTypeValue editCompanyType(CompanyTypeValue companyTypeValue) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(companyTypeValue.getTenantValue().getUuid());
        CompanyEntity companyEntity = companyRepository.getCompanyEntityByUuid(companyTypeValue.getCompanyValue().getCompanyUuid());
        CompanyTypeEntity companyTypeTemp = companyTypeRepository.findByUuid(companyTypeValue.getCompanyTypeUuid());

        CompanyTypeEntity companyTypeEntity = companyTypeValue.toEntity().toBuilder()
                .id(companyTypeTemp.getId())
                //.tenantEntity(tenantEntity)
                .companyEntiies(Arrays.asList(companyEntity))
                .build();

        return companyTypeRepository.save(companyTypeEntity).toDTO();
    }

    @Override
    public List<CompanyTypeValue>  getAllCompanyType(String tenantUuid) {
/*
        List<CompanyTypeValue> companyTypeValues = new ArrayList<>();
        CompanyTypeValue companyTypeValue = null;
        List<CompanyTypeEntity> companyTypeEntities = companyTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
        for (int i = 0; i < companyTypeEntities.size(); i++) {
            companyTypeValue = new CompanyTypeValue();
            BeanUtils.copyProperties(companyTypeEntities.get(i), companyTypeValue);
            companyTypeValue.setTenantUuid(tenantUuid);
            companyTypeValues.add(companyTypeValue);
        }
*/
       // List<CompanyTypeEntity> companyTypeEntities = companyTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
       // return companyTypeEntities.stream().map(cte -> cte.toDTO()).collect(Collectors.toList());
        return null;
    }

    @Override
    public CompanyTypeValue getCompanyType(String tenantUuid, String companyTypeUuid) {
        //CompanyTypeValue companyTypeValue = new CompanyTypeValue();

        //CompanyTypeEntity companyTypeEntity = companyTypeRepository.findByTenantEntity_UuidAndCompanyTypeUuid(tenantUuid, companyTypeUuid);
        //BeanUtils.copyProperties(companyTypeEntity, companyTypeValue);
        //companyTypeValue.setTenantUuid(tenantUuid);
        //CompanyTypeEntity companyTypeEntity = companyTypeRepository.findByTenantEntity_UuidAndCompanyTypeUuid(tenantUuid, companyTypeUuid);
        //return companyTypeEntity.toDTO();
        return null;
    }

    @Override
    public CompanyTypeValue deleteCompanyType(String tenantUuid, String companyTypeUuid) {
        companyTypeRepository.softDelete(companyTypeUuid);
       // CompanyTypeEntity companyTypeEntity = companyTypeRepository.findByTenantEntity_UuidAndCompanyTypeUuid(tenantUuid, companyTypeUuid);
        //BeanUtils.copyProperties(companyTypeEntity, companyTypeValue);
        //return companyTypeEntity.toDTO();
        return null;
    }

}
