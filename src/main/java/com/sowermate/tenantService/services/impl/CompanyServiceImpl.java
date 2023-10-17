package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Override
    public CompanyValue createCompany(CompanyValue companyValue) {
        return prepareAndSaveEntities(companyValue);
    }

    @Override
    public CompanyValue editCompany(CompanyValue companyValue) {
        return prepareAndSaveEntities(companyValue);
    }

    @Override
    public CompanyValue getCompany(String tenantUuid, String companyUuid) {
        CompanyEntity companyEntity = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUuid, companyUuid);
        AddressEntity addressEntity = companyEntity.getAddresses().get(0);
        return companyEntity.toDTO().toBuilder().addresses(
                Arrays.asList(addressEntity.toDTO().toBuilder()
                        .addressTypeUuid(addressEntity.getAddressType().getUuid()).build())).build();
    }

    public CompanyEntity getCompanyEntity(String tenantUuid, String companyUuid){
        CompanyEntity companyEntity = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUuid, companyUuid);
        if(companyEntity==null){
            throw new ResourceNotFoundException("Company","tenantUuid or companyUuid",tenantUuid +" or "+companyUuid);
        }
        return companyEntity;
    }
    @Override
    public List<CompanyValue> getAllCompany(String tenantUuid) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUuid);
        return getTenantWiseAddressDetails(tenantEntity);
    }

    @Override
    public CompanyValue deleteCompany(String tenantUuid, String companyUuid) {
        CompanyEntity companyEntity = getCompanyEntity(tenantUuid,companyUuid);
        companyRepository.softDelete(companyEntity.getUuid());
        return getCompany(tenantUuid, companyUuid);
    }

    private CompanyValue prepareAndSaveEntities(CompanyValue companyValue) {

        TenantEntity tenantEntity = tenantRepository.findByUuid(companyValue.getTenantUuid());
        CompanyTypeEntity companyType = companyTypeRepository.findByUuid(companyValue.getCompanyTypeUuid());

        CompanyEntity companyEntity = prepareAndSaveCompanyEntity(companyValue, tenantEntity, companyType);
        AddressEntity addressEntity = prepareAndSaveAddressEntity(companyValue, companyEntity, tenantEntity);

        return companyEntity.toDTO().toBuilder().addresses(Arrays.asList(addressEntity.toDTO().toBuilder().addressTypeUuid(addressEntity.getAddressType().getUuid()).build())).build();
    }

    private CompanyEntity prepareAndSaveCompanyEntity(CompanyValue companyValue, TenantEntity tenantEntity, CompanyTypeEntity companyType) {
        if (null == companyValue.getUuid()) {
            return companyRepository.save(companyValue.toEntity().toBuilder().tenantEntity(tenantEntity).companyType(companyType).build());
        } else {
            CompanyEntity companyEntityTemp = getCompanyEntity(companyValue.getTenantUuid(), companyValue.getUuid());

            return companyRepository.save(companyValue.toEntity().toBuilder()
                    .id(companyEntityTemp.getId())
                    .tenantEntity(tenantEntity)
                    .companyType(companyType)
                    .createdDateTime(companyEntityTemp.getCreatedDateTime())
                    .createdBy(companyEntityTemp.getCreatedBy())
                    .build());
        }
    }

    private AddressEntity prepareAndSaveAddressEntity(CompanyValue companyValue, CompanyEntity companyEntity, TenantEntity tenantEntity) {
        AddressTypeEntity addressType = addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(companyValue.getTenantUuid(), companyValue.getAddresses().get(0).getAddressTypeUuid());
        AddressValue addressValue = companyValue.getAddresses().get(0);
        if (addressValue.getUuid() == null) {
            AddressEntity addressEntity = addressValue.toEntity().toBuilder().addressType(addressType).company(companyEntity).build();
            return addressRepository.save(addressEntity);
        } else {
            AddressEntity addressEntityTemp = addressRepository.findByUuid(addressValue.getUuid());
            AddressEntity addressEntity = addressValue.toEntity().toBuilder()
                    .id(addressEntityTemp.getId())
                    .addressType(addressType).company(companyEntity)
                    .createdDateTime(addressEntityTemp.getCreatedDateTime())
                    .createdBy(addressEntityTemp.getCreatedBy())
                    .build();
            return addressRepository.save(addressEntity);
        }
    }

    private List<CompanyValue> getTenantWiseAddressDetails(TenantEntity tenantEntity) {
        List<CompanyEntity> companyEntities = companyRepository.findAllByTenantEntityId(tenantEntity.getId());
        return companyEntities.stream().map(
                ce -> ce.toDTO().toBuilder()
                        .addresses(ObjectUtils.isEmpty(ce.getAddresses()) ?
                                null : Arrays.asList(ce.getAddresses().get(0).toDTO()))
                        .build()
                ).collect(Collectors.toList());
    }
}