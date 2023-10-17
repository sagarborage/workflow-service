package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.PiTypeEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.PiTypeValue;
import com.sowermate.tenantService.repositories.PiTypeRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.PiTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class PiTypeServiceImpl implements PiTypeService {

    @Autowired
    private PiTypeRepository piTypeRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public PiTypeValue createPiType(PiTypeValue piTypeValue) {

        TenantEntity tenantEntity = tenantRepository.findByUuid(piTypeValue.getTenantUuid());
        PiTypeEntity piTypeEntity = piTypeValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .build();
        return piTypeRepository.save(piTypeEntity).toDTO();

    }

    @Override
    public List<PiTypeValue> getAllPiType(String tenantUuid) {
        List<PiTypeEntity> piTypeEntities = piTypeRepository.findAllByTenantEntityUuid(tenantUuid);
        return piTypeEntities.stream().map(pte -> pte.toDTO()).collect(Collectors.toList());
    }

    @Override
    public PiTypeValue editPiType(PiTypeValue piTypeValue) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(piTypeValue.getTenantUuid());
        PiTypeEntity tempPiTypeEntity =  piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(piTypeValue.getTenantUuid(),
                piTypeValue.getUuid());
        PiTypeEntity piTypeEntity = piTypeValue.toEntity().toBuilder()
                .id(tempPiTypeEntity.getId())
                .tenantEntity(tenantEntity)
                .createdDateTime(tempPiTypeEntity.getCreatedDateTime())
                .createdBy(tempPiTypeEntity.getCreatedBy())
                .build();
        return piTypeRepository.save(piTypeEntity).toDTO();
    }

    @Override
    public PiTypeValue getPiType(String tenantUuid, String piTypeUuid) {
        return piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(tenantUuid, piTypeUuid).toDTO();
    }


    @Override
    public int deletePiType(String tenantUuid, String piTypeUuid) {
        return piTypeRepository.deleteByUuid(piTypeUuid);
    }
}
