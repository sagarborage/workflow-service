package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private CompanyAddressRepository companyAddressRepository;
    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Override
    public CompanyValue createCompany(CompanyValue companyValue) throws Exception {
        return prepareAndSaveEntities(companyValue);
    }

    @Override
    public CompanyValue editCompany(CompanyValue companyValue) throws Exception {
        return prepareAndSaveEntities(companyValue);
    }

    @Override
    public CompanyValue deleteCompany(String tenantUuid, String companyUuid) throws Exception {
        companyRepository.softDelete(companyUuid);
        return getCompany(tenantUuid, companyUuid);
    }

    @Override
    public CompanyValue getCompany(String tenantUuid, String companyUuid) throws Exception {
        CompanyValue companyValue = CompanyValue.newBuilder().build();
        CompanyEntity companyEntity = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUuid, companyUuid);
        BeanUtils.copyProperties(companyEntity.getCompanyType(), companyValue);
        BeanUtils.copyProperties(companyEntity, companyValue);
        return companyValue;
    }

    @Override
    public List<CompanyValue> getAllCompany(String tenantUuid) throws Exception {
        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(tenantUuid);
        return getTenantWiseAddressDetails(tenantEntity);
    }

    private CompanyValue prepareAndSaveEntities(CompanyValue companyValue) {

        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(companyValue.getTenantUuid());
        CompanyTypeEntity cte = companyTypeRepository.findByCompanyTypeUuid(companyValue.getCompanyTypeUuid());

        AddressEntity addressEntity = prepareAndSaveAddressEntity(companyValue, tenantEntity);

        CompanyEntity companyEntity = prepareAndSaveCompanyEntity(companyValue, tenantEntity, cte);

        List<CompanyAddressEntity> companyAddresses = prepareAndSaveCompanyAddresses(companyValue, companyEntity, addressEntity);

        return companyRepository.save(companyEntity).toDTO().toBuilder()
                .companyAddresses(companyAddresses.stream().map(ca -> ca.toDTO()).collect(Collectors.toList())).build();
    }

    private List<CompanyAddressEntity> prepareAndSaveCompanyAddresses(CompanyValue companyValue, CompanyEntity companyEntity, AddressEntity addressEntity) {
        if(null == companyValue.getCompanyAddresses().get(0).getCompanyAddressUuid()){
           return companyAddressRepository.saveAll(companyValue.getCompanyAddresses().stream()
                    .map(cae -> cae.toEntity().toBuilder().companyAddressUuid(CommonUtils.generateUUID())
                            .addressTypeEntity(addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(companyValue.getTenantUuid(), cae.getAddressTypeUuid()))
                            .companyEntity(companyEntity)
                            .addressEntity(addressEntity)
                            .build())
                    .collect(Collectors.toList()));
        } else {
            CompanyAddressEntity companyAddress =  companyAddressRepository.findByCompanyAddressUuid(
                    companyValue.getCompanyAddresses().get(0).getCompanyAddressUuid());
            return companyAddressRepository.saveAll(companyValue.getCompanyAddresses().stream()
                    .map(cae -> cae.toEntity().toBuilder().companyAddressId(companyAddress.getCompanyAddressId())
                            .addressTypeEntity(addressTypeRepository.findByTenantEntity_UuidAndAddressTypeUuid(companyValue.getTenantUuid(), cae.getAddressTypeUuid()))
                            .companyEntity(companyEntity)
                            .addressEntity(addressEntity)
                            .build())
                    .collect(Collectors.toList()));
        }
    }

    private CompanyEntity prepareAndSaveCompanyEntity(CompanyValue companyValue, TenantEntity tenantEntity, CompanyTypeEntity cte) {
        if (null == companyValue.getCompanyUuid()) {
            return  companyRepository.save(companyValue.toEntity().toBuilder()
                    .companyUuid(CommonUtils.generateUUID())
                    .tenantEntity(tenantEntity)
                    .companyType(cte)
                    .build());
        } else {
            CompanyEntity tempCompanyEntity = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(companyValue.getTenantUuid(), companyValue.getCompanyUuid());
            return companyRepository.save(companyValue.toEntity().toBuilder()
                    .companyId(tempCompanyEntity.getCompanyId())
                    .tenantEntity(tenantEntity)
                    .companyType(cte)
                    .build());
        }
    }

    private AddressEntity prepareAndSaveAddressEntity(CompanyValue companyValue, TenantEntity tenantEntity) {

        AddressValue addressValue = companyValue.getCompanyAddresses().get(0).getAddressValue();
        if (addressValue.getAddressUuid() == null) {
            AddressEntity addressEntity = addressValue.toEntity().toBuilder()
                    .addressUuid(CommonUtils.generateUUID())
                    .build();
            return addressRepository.save(addressEntity);
        } else {
            AddressEntity tempAddressEntity = addressRepository.findByAddressUuid(addressValue.getAddressUuid());
            return addressValue.toEntity().toBuilder()
                    .addressId(tempAddressEntity.getAddressId())
                    .build();
        }
    }


    private List<CompanyValue> getTenantWiseAddressDetails(TenantEntity tenantEntity) {
        List<CompanyEntity> companyEntities = companyRepository.findAllByTenantEntityTenantId(tenantEntity.getTenantId());
        return companyEntities.stream().map(ce -> ce.toDTO()).collect(Collectors.toList());
    }
}


