package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.entities.value.CompanyAddressValue;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        CompanyEntity companyEntity = prepareAndSaveEntities(companyValue);

        BeanUtils.copyProperties(companyEntity, companyValue);
        return companyValue;
    }

    private CompanyEntity prepareAndSaveEntities(CompanyValue companyValue) {

        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(companyValue.getTenantUuid());

        AddressEntity addressEntity = prepareAndSaveAddressEntity(companyValue, tenantEntity);

        CompanyEntity companyEntity = prepareAndSaveCompanyEntity(companyValue, tenantEntity);

        CompanyAddressEntity companyAddressEntity = prepareAndSaveCompanyAddressEntity(companyValue, tenantEntity, addressEntity, companyEntity);

        return companyEntity;
    }

    private CompanyEntity prepareAndSaveCompanyEntity(CompanyValue companyValue, TenantEntity tenantEntity) {
        CompanyEntity companyEntity = null;

        if (companyValue != null) {
            if (companyValue.getCompanyUuid() != null) {
                companyEntity = companyRepository.getCompanyEntityByCompanyUuid(companyValue.getCompanyUuid());
                BeanUtils.copyProperties(companyValue, companyEntity);
            } else {
                companyEntity = new CompanyEntity();
                BeanUtils.copyProperties(companyValue, companyEntity);
                companyEntity.setCompanyUuid(CommonUtils.generateUUID());
            }
            if (companyValue.getIsActive() == null) {
                companyEntity.setIsActive(true);
            }
        }

        CompanyTypeEntity companyTypeEntity = companyTypeRepository.findByCompanyTypeUuid(companyValue.getCompanyTypeUuid());
        if (companyTypeEntity != null) {
            companyEntity.setCompanyTypeEntity(companyTypeEntity);
        }
        if (tenantEntity != null) {
            companyEntity.setTenantEntity(tenantEntity);
        }
        companyRepository.save(companyEntity);
        return companyEntity;
    }

    /**
     * Helps to prepare and save the {@link CompanyAddressEntity} object.
     *
     * @param companyValue the {@link CompanyValue} value object from UI
     * @param tenantEntity the {@link TenantEntity} object
     * @param addressEntity the {@link AddressEntity} object
     * @param companyEntity the {@link CompanyEntity} object
     * @return the saved {@link CompanyAddressEntity} object
     */
    private CompanyAddressEntity prepareAndSaveCompanyAddressEntity(CompanyValue companyValue, TenantEntity tenantEntity, AddressEntity addressEntity, CompanyEntity companyEntity) {
        CompanyAddressEntity companyAddressEntity = null;

        if (!companyValue.getCompanyAddresses().isEmpty()) {
            if (companyValue.getCompanyAddresses().get(0).getCompanyAddressUuid() != null) {
                companyAddressEntity = companyAddressRepository.getCompanyAddressEntityByCompanyAddressUuid(companyValue.getCompanyAddresses().get(0).getCompanyAddressUuid());
                BeanUtils.copyProperties(companyValue.getCompanyAddresses().get(0), companyAddressEntity);
            } else {
                companyAddressEntity = new CompanyAddressEntity();
                BeanUtils.copyProperties(companyValue.getCompanyAddresses().get(0), companyAddressEntity);
                companyAddressEntity.setCompanyAddressUuid(CommonUtils.generateUUID());
            }
            if (companyValue.getCompanyAddresses().get(0).getIsActive() == null) {
                companyAddressEntity.setIsActive(true);
            }
        }

        if (tenantEntity != null) {
            companyAddressEntity.setTenantEntity(tenantEntity);
        }
        AddressTypeEntity addressType = addressTypeRepository.getAddressTypeEntityByAddressTypeUuid(companyValue.getCompanyAddresses().get(0).getAddressTypeUuid());
        if (addressType != null) {
            companyAddressEntity.setAddressTypeEntity(addressType);
        }
        companyAddressEntity.setAddressEntity(addressEntity);
        companyAddressEntity.setCompanyEntity(companyEntity);
        companyAddressRepository.save(companyAddressEntity);

        //companyEntity.setCompanyAddressEntities(Arrays.asList(companyAddressEntity));

        AddressValue addressValue = new AddressValue();
        BeanUtils.copyProperties(addressEntity, addressValue);
        companyValue.getCompanyAddresses().get(0).setAddress(addressValue);

        CompanyAddressValue companyAddressValue = new CompanyAddressValue();
        BeanUtils.copyProperties(companyValue.getCompanyAddresses().get(0), companyAddressValue);
        BeanUtils.copyProperties(companyAddressEntity, companyAddressValue);
        companyAddressValue.setAddress(addressValue);
        companyValue.getCompanyAddresses().clear();
        companyValue.getCompanyAddresses().add(companyAddressValue);

        return companyAddressEntity;
    }

    private AddressEntity prepareAndSaveAddressEntity(CompanyValue companyValue, TenantEntity tenantEntity) {
        AddressEntity addressEntity = null;
        if (!companyValue.getCompanyAddresses().isEmpty() &&
                companyValue.getCompanyAddresses().get(0).getAddress() != null) {
            if (companyValue.getCompanyAddresses().get(0).getAddress().getAddressUuid() != null) {
                addressEntity = addressRepository.getAddressEntityByAddressUuid(companyValue.getCompanyAddresses().get(0).getAddress().getAddressUuid());
                BeanUtils.copyProperties(companyValue.getCompanyAddresses().get(0).getAddress(), addressEntity);
            } else {
                addressEntity = new AddressEntity();
                BeanUtils.copyProperties(companyValue.getCompanyAddresses().get(0).getAddress(), addressEntity);
                addressEntity.setAddressUuid(CommonUtils.generateUUID());
            }

        }

        if (tenantEntity != null) {
            addressEntity.setTenantEntity(tenantEntity);
        }
        addressRepository.save(addressEntity);

        return addressEntity;
    }

    @Override
    public CompanyValue editCompany(CompanyValue companyValue) throws Exception {
        CompanyEntity companyEntity = prepareAndSaveEntities(companyValue);
        BeanUtils.copyProperties(companyEntity, companyValue);
        return companyValue;
    }

    @Override
    public CompanyValue getCompany(String tenantUuid, String companyUuid) throws Exception {
        CompanyValue companyValue = new CompanyValue();
        CompanyEntity companyEntity = companyRepository.findByTenantEntity_UuidAndCompanyUuid(tenantUuid, companyUuid);
        BeanUtils.copyProperties(companyEntity.getCompanyTypeEntity(), companyValue);
        BeanUtils.copyProperties(companyEntity, companyValue);
        companyValue.setTenantUuid(tenantUuid);
        companyValue.setCompanyTypeUuid(companyValue.getCompanyTypeUuid());
        return companyValue;
    }

    @Override
    public CompanyValue deleteCompany(String tenantUuid, String companyUuid) throws Exception {
        CompanyValue companyValue = new CompanyValue();
        companyRepository.softDelete(companyUuid);
        CompanyEntity companyEntity = companyRepository.findByTenantEntity_UuidAndCompanyUuid(tenantUuid, companyUuid);
        BeanUtils.copyProperties(companyEntity, companyValue);
        return companyValue;
    }

    @Override
    public List<CompanyValue> getAllCompany(String tenantUuid) throws Exception {
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


