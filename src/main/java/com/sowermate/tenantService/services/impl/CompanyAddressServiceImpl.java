package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.CompanyAddressEntity;
import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.value.CompanyAddressValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.CompanyAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackForClassName = {"Exception"})
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
        CompanyEntity companyEntity = companyRepository.findByCompanyUuid(companyAddressValue.getCompanyUuid());
        CompanyAddressEntity companyAddressEntity = companyAddressValue.toEntity().toBuilder()
                .companyAddressUuid(CommonUtils.generateUUID())
                .companyEntity(companyEntity)
                .addressTypeEntity(addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(companyEntity.getTenantEntity().getUuid(),
                        companyAddressValue.getAddressTypeUuid()))
                .addressEntity(addressRepository.findByAddressUuid(companyAddressValue.getAddressValue().getAddressUuid()))
                .build();
/*        BeanUtils.copyProperties(companyAddressValue, companyAddressEntity);
        companyAddressEntity.setCompanyAddressUuid(CommonUtils.generateUUID());
        //companyAddressEntity.setTenantEntity(tenantRepository.findByTenantUuid(companyAddressValue.getTenantUuid()));
        companyAddressEntity.setAddressTypeEntity(addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(companyAddressValue.getTenantUuid(), companyAddressValue.getAddressTypeUuid()));
        companyAddressEntity.setCompanyEntity(companyRepository.findByCompanyEntityUuid(companyAddressValue.getCompanyValue().getCompanyUuid()));
        companyAddressEntity.setAddressEntity(addressRepository.findByAddressUuid(companyAddressValue.getAddressUuid()));
        BeanUtils.copyProperties(companyAddressRepository.save(companyAddressEntity), companyAddressValue);*/
        return companyAddressRepository.save(companyAddressEntity).toDTO();
    }

    @Override
    public CompanyAddressValue editCompanyAddress(CompanyAddressValue companyAddressValue) throws Exception {
        CompanyEntity companyEntity = companyRepository.findByCompanyUuid(companyAddressValue.getCompanyUuid());
        CompanyAddressEntity companyAddressEntity = companyAddressValue.toEntity().toBuilder()
                .companyEntity(companyEntity)
                .addressTypeEntity(addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(companyEntity.getTenantEntity().getUuid(),
                        companyAddressValue.getAddressTypeUuid()))
                .addressEntity(addressRepository.findByAddressUuid(companyAddressValue.getAddressValue().getAddressUuid()))
                .build();
/*        CompanyAddressEntity companyAddressEntity = new CompanyAddressEntity();
        BeanUtils.copyProperties(companyAddressValue, companyAddressEntity);
        //companyAddressEntity.setCompanyAddressId(companyAddressRepository.findByTenantEntity_UuidAdCompanyAddressUuid(companyAddressValue.getTenantUuid(), companyAddressValue.getCompanyAddressUuid()).getCompanyAddressId());
        //companyAddressEntity.setTenantEntity(tenantRepository.findByTenantUuid(companyAddressValue.getTenantUuid()));
        companyAddressEntity.setAddressTypeEntity(addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(companyAddressValue.getTenantUuid(), companyAddressValue.getAddressTypeUuid()));
        companyAddressEntity.setCompanyEntity(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(companyAddressValue.getTenantUuid(), companyAddressValue.getCompanyUuid()));
        companyAddressEntity.setAddressEntity(addressRepository.findByAddressUuid(companyAddressValue.getAddressUuid()));
        BeanUtils.copyProperties(companyAddressRepository.save(companyAddressEntity), companyAddressValue);*/
        return companyAddressRepository.save(companyAddressEntity).toDTO();
    }

    @Override
    public CompanyAddressValue getCompanyAddress(String companyAddressUuid) throws Exception {
/*        CompanyAddressValue companyAddressValue = new CompanyAddressValue();
        CompanyAddressEntity companyAddressEntity = companyAddressRepository.findByTenantEntity_UuidAndCompanyAddressUuid(tenantUuid, companyAddressUuid);
        BeanUtils.copyProperties(companyAddressEntity.getCompanyEntity(), companyAddressValue);
        BeanUtils.copyProperties(companyAddressEntity.getAddressEntity(), companyAddressValue);
        BeanUtils.copyProperties(companyAddressEntity.getAddressTypeEntity(), companyAddressValue);
        BeanUtils.copyProperties(companyAddressEntity, companyAddressValue);
        companyAddressValue.setTenantUuid(tenantUuid);
        companyAddressValue.setCompanyUuid(companyAddressValue.getCompanyUuid());*/
        return companyAddressRepository.findByCompanyAddressUuid(companyAddressUuid).toDTO();
    }

    @Override
    public List<CompanyAddressValue> getAllCompanyAddress(String tenantUuid) throws Exception {
        List<CompanyAddressValue> companyAddressValues = new ArrayList<>();
        //TODO: implement when required
        return companyAddressValues;
    }

    @Override
    public int deleteCompanyAddress(String tenantUuid, String companyAddressUuid) throws Exception {
        //TODO: implement when required by passing object
        return companyAddressRepository.softDelete(companyAddressUuid);
        /*  CompanyAddressValue companyAddressValue = new CompanyAddressValue();
       CompanyAddressEntity companyAddressEntity = companyAddressRepository.findByTenantEntity_UuidAndCompanyAddressUuid(tenantUuid, companyAddressUuid);
       BeanUtils.copyProperties(companyAddressEntity, companyAddressValue);
        return companyAddressValue;*/
    }
}