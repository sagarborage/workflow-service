package com.sowermate.tenantService.mapper;

import com.sowermate.workflow.domain.entities.CompanyEntity;
import com.sowermate.workflow.domain.entities.value.CompanyValue;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    //@Mapping(target = "companyAddresses", source = "companyAddresses")
    //CompanyValue toDTO(CompanyEntity companyEntity);

    //@Mapping(target = "companyAddresses", ignore = true)
    //CompanyEntity toEntity(CompanyValue companyValue);

}
