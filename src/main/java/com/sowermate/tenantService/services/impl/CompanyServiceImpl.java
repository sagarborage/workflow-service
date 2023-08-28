package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.entities.value.CompanyAddressValue;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.mapper.CompanyMapper;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        CompanyEntity companyEntity = prepareAndSaveEntities(companyValue);

        //BeanUtils.copyProperties(companyEntity, companyValue);
        return companyEntity.toDTO();
    }

    @Override
    public CompanyValue editCompany(CompanyValue companyValue) throws Exception {
        CompanyEntity companyEntity = prepareAndSaveEntities(companyValue);
        //BeanUtils.copyProperties(companyEntity, companyValue);
        return companyEntity.toDTO();
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
        //companyValue.toBuilder().companyTypeUuid(tenantUuid);
        //companyValue.setCompanyTypeUuid(companyValue.getCompanyTypeUuid());

        return companyValue;
    }

    @Override
    public List<CompanyValue> getAllCompany(String tenantUuid) throws Exception {

        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(tenantUuid);
        return getTenantWiseAddressDetails(tenantEntity);
    }

    private CompanyEntity prepareAndSaveEntities(CompanyValue companyValue) {
        //CompanyEntity company = companyValue.toEntity();
        CompanyEntity entity1 = CompanyMapper.INSTANCE.toEntity(companyValue);
        //TenantEntity tenantEntity = tenantRepository.findByTenantUuid(companyValue.getCompanyUuid());
        //CompanyEntity ce = CompanyMapper.INSTANCE.toEntity(companyValue);
        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(companyValue.getTenantValue().getUuid());
        //AddressEntity addressEntity = prepareAndSaveAddressEntity(companyValue, tenantEntity);

        //CompanyEntity companyEntity = prepareAndSaveCompanyEntity(companyValue, tenantEntity);

        //CompanyAddressEntity companyAddressEntity = prepareAndSaveCompanyAddressEntity(companyValue, tenantEntity, addressEntity, companyEntity);
        CompanyEntity companyEntity = companyValue.toEntity().toBuilder()
                .companyUuid(CommonUtils.generateUUID())
                .tenantEntity(tenantEntity)
                .companyType(companyTypeRepository.findByCompanyTypeUuid(companyValue.getCompanyType().getCompanyTypeUuid()))
/*                .companyAddressEntities(companyValue.getCompanyAddresses().stream()
                        .map(cae-> cae.toEntity().toBuilder().companyAddressUuid(CommonUtils.generateUUID())
                                .addressEntity(cae.getAddressValue().toEntity().toBuilder().addressUuid(CommonUtils.generateUUID()).build())
                                .build())
                        .collect(Collectors.toList()))*/
                .build();

        return companyRepository.save(companyEntity);
    }
/*

    private CompanyEntity prepareAndSaveCompanyEntity(CompanyValue companyValue, TenantEntity tenantEntity) {
        CompanyEntity companyEntity = null;

        if (companyValue != null) {
            if (companyValue.getCompanyUuid() != null) {
                companyEntity = companyRepository.getCompanyEntityByCompanyUuid(companyValue.getCompanyUuid());
                BeanUtils.copyProperties(companyValue, companyEntity);
            } else {
                companyEntity = CompanyEntity.newBuilder().build();
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
     *//*

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

*/
/*        if (tenantEntity != null) {
            companyAddressEntity.setTenantEntity(tenantEntity);
        }

        AddressTypeEntity addressType = addressTypeRepository.getAddressTypeEntityByAddressTypeUuid(companyValue.getCompanyAddresses().get(0).getAddressTypeUuid());
        if (addressType != null) {
            companyAddressEntity.setAddressTypeEntity(addressType);
        }
        companyAddressEntity.setAddressEntity(addressEntity);
        companyAddressEntity.setCompanyEntity(companyEntity);
        companyAddressRepository.save(companyAddressEntity);

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
*/

    private List<CompanyValue> getTenantWiseAddressDetails(TenantEntity tenantEntity) {
        List<CompanyEntity> companyEntities =  companyRepository.findAllByTenantEntityTenantId(tenantEntity.getTenantId());
        return companyEntities.stream().map(ce -> ce.toDTO()).collect(Collectors.toList());
/*
        List<CompanyAddressEntity> companyAddressEntities = companyAddressRepository.getCompanyAddressEntityByTenantEntity_TenantId(tenantEntity.getTenantId());
        Map<Integer, CompanyAddressEntity> companyAddressMap = companyAddressEntities.stream().collect(Collectors.toMap(addressEntity->addressEntity.getCompanyEntity().getCompanyId(), addressEntity-> addressEntity));
        List<AddressEntity> addressDetails = addressRepository.findAllByTenantEntity_TenantId(tenantEntity.getTenantId());
        Map<Integer, AddressEntity> addressDetailsMap = addressDetails.stream().collect(Collectors.toMap(address->address.getAddressId(), address->address));
*/


/*        return companyEntities.stream().map(entity ->{
            CompanyValue companyValue = new CompanyValue();
            BeanUtils.copyProperties(entity.getCompanyTypeEntity(), companyValue);
            BeanUtils.copyProperties(entity, companyValue);
            //companyValue.setTenantUuid(tenantEntity.getUuid());
            //companyValue.setCompanyTypeUuid(companyValue.getCompanyTypeUuid());

            AddressValue addressDetail = new AddressValue();
           // BeanUtils.copyProperties(addressDetailsMap.get(companyAddressMap.get(entity.getCompanyId()).getAddressEntity().getAddressId()), addressDetail);

            return companyValue;
        }).collect(Collectors.toList());*/
    }
}


