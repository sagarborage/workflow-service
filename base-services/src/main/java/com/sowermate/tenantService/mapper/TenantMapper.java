package com.sowermate.tenantService.mapper;

import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.TenantValue;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TenantMapper {
    //TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);

    //TenantValue toDTO(TenantEntity tenantEntity);

    //TenantEntity toEntity(TenantValue tenantValue);
}
