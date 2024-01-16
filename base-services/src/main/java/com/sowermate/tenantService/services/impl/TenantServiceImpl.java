package com.sowermate.tenantService.services.impl;

import com.sowermate.base.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.TenantValue;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public TenantValue saveTenantDetails(TenantValue tenantValue) {
        TenantEntity tenantEntity = tenantValue.toEntity().toBuilder()
                .uuid(CommonUtils.generateUUID())
                .build();
        return tenantRepository.save(tenantEntity).toDTO();
    }

    @Override
    public List<TenantValue> getAllTenantDetails() {
        return tenantRepository.findAll().stream().map(t -> t.toDTO()).collect(Collectors.toList());
    }

    @Override
    public TenantValue editTenantDetails(TenantValue tenantValue) {
        TenantEntity tempTenantEntity = tenantRepository.findByUuid(tenantValue.getUuid());
        TenantEntity tenantEntity = tenantValue.toEntity().toBuilder()
                .id(tempTenantEntity.getId())
                .createdDateTime(tempTenantEntity.getCreatedDateTime())
                .createdBy(tempTenantEntity.getCreatedBy())
                .build();
        return tenantRepository.save(tenantEntity).toDTO();
    }


    @Override
    public TenantValue getTenantDetails(String tenantUuid) {
        return tenantRepository.findByUuid(tenantUuid).toDTO();
    }

    @Override
    public TenantValue deleteTenantDetails(String tenantUuid) {
        tenantRepository.softDelete(tenantUuid);
        return tenantRepository.findByUuid(tenantUuid).toDTO();
    }


    /**
     * Retrieves the ID of a tenant based on its UUID.
     *
     * @param tenantUuid The UUID of the tenant for which the ID is being retrieved.
     * @return The ID of the tenant if found, or throws a ResourceNotFoundException if not found.
     * @throws com.sowermate.base.exceptions.ResourceNotFoundException If the tenant with the specified UUID is not found.
     */
    @Override
    public Long getTenantId(String tenantUuid) {
        return this.tenantRepository.findIdByUuid(tenantUuid)
                .orElseThrow(() -> new ResourceNotFoundException("tenant", "tenantUuid", tenantUuid));
    }
}
