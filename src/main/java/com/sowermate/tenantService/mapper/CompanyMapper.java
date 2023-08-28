package com.sowermate.tenantService.mapper;

import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.value.CompanyValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    //@Mapping(target = "companyAddresses", source = "companyAddresses")
    CompanyValue toDTO(CompanyEntity companyEntity);

    //@Mapping(target = "companyAddresses", ignore = true)
    CompanyEntity toEntity(CompanyValue companyValue);

}
