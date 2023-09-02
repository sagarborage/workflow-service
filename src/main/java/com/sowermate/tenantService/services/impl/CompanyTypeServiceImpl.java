package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.CompanyTypeEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.CompanyTypeValue;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.CompanyTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.CompanyTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public CompanyTypeValue createCompanyType(CompanyTypeValue companyTypeValue) throws Exception {
        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(companyTypeValue.getTenantValue().getUuid());
        CompanyEntity companyEntity = companyRepository.getCompanyEntityByCompanyUuid(companyTypeValue.getCompanyValue().getCompanyUuid());
        CompanyTypeEntity companyTypeEntity = companyTypeValue.toEntity().toBuilder()
                .companyTypeUuid(CommonUtils.generateUUID())
                .tenantEntity(tenantEntity)
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
    public CompanyTypeValue editCompanyType(CompanyTypeValue companyTypeValue) throws Exception {
/*        CompanyTypeEntity companyTypeEntity = new CompanyTypeEntity();
        BeanUtils.copyProperties(companyTypeValue, companyTypeEntity);

        //Check that UUID is not null before searching for the tenant
        if (companyTypeValue.getCompanyTypeUuid() != null) {
            CompanyTypeEntity matchingCompanyTypes = companyTypeRepository.findByTenantEntity_UuidAndCompanyTypeUuid(companyTypeValue.getTenantUuid(), companyTypeValue.getCompanyTypeUuid());
            if (matchingCompanyTypes != null) {
                companyTypeEntity.setCompanyTypeId(matchingCompanyTypes.getCompanyTypeId());
                //companyTypeEntity.setTenantEntity(tenantRepository.findByTenantUuid(companyTypeValue.getTenantUuid()));
                BeanUtils.copyProperties(companyTypeRepository.save(companyTypeEntity), companyTypeValue);
            } else {
                throw new Exception("No tenant found with UUID " + companyTypeValue.getCompanyTypeUuid());
            }
        } else {
            throw new Exception("UUID cannot be null");
        }*/

        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(companyTypeValue.getTenantValue().getUuid());
        CompanyEntity companyEntity = companyRepository.getCompanyEntityByCompanyUuid(companyTypeValue.getCompanyValue().getCompanyUuid());
        CompanyTypeEntity companyTypeTemp = companyTypeRepository.findByCompanyTypeUuid(companyTypeValue.getCompanyTypeUuid());

        CompanyTypeEntity companyTypeEntity = companyTypeValue.toEntity().toBuilder()
                .companyTypeId(companyTypeTemp.getCompanyTypeId())
                .tenantEntity(tenantEntity)
                .companyEntiies(Arrays.asList(companyEntity))
                .build();

        return companyTypeRepository.save(companyTypeEntity).toDTO();
    }

    @Override
    public List<CompanyTypeValue>  getAllCompanyType(String tenantUuid) throws Exception {
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
        List<CompanyTypeEntity> companyTypeEntities = companyTypeRepository.findAllByTenantEntity_Uuid(tenantUuid);
        return companyTypeEntities.stream().map(cte -> cte.toDTO()).collect(Collectors.toList());
    }

    @Override
    public CompanyTypeValue getCompanyType(String tenantUuid, String companyTypeUuid) throws Exception {
        //CompanyTypeValue companyTypeValue = new CompanyTypeValue();

        //CompanyTypeEntity companyTypeEntity = companyTypeRepository.findByTenantEntity_UuidAndCompanyTypeUuid(tenantUuid, companyTypeUuid);
        //BeanUtils.copyProperties(companyTypeEntity, companyTypeValue);
        //companyTypeValue.setTenantUuid(tenantUuid);
        CompanyTypeEntity companyTypeEntity = companyTypeRepository.findByTenantEntity_UuidAndCompanyTypeUuid(tenantUuid, companyTypeUuid);
        return companyTypeEntity.toDTO();
    }

    @Override
    public CompanyTypeValue deleteCompanyType(String tenantUuid, String companyTypeUuid) throws Exception {
        companyTypeRepository.softDelete(companyTypeUuid);
        CompanyTypeEntity companyTypeEntity = companyTypeRepository.findByTenantEntity_UuidAndCompanyTypeUuid(tenantUuid, companyTypeUuid);
        //BeanUtils.copyProperties(companyTypeEntity, companyTypeValue);
        return companyTypeEntity.toDTO();
    }

}
