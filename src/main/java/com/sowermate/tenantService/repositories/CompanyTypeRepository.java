package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.CompanyTypeEntity;
import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyTypeRepository extends JpaRepository<CompanyTypeEntity ,String> {

  //  public List<CompanyTypeEntity> findByCompanyTypeId(int companyTypeId);

    public CompanyTypeEntity findByCompanyTypeUuid(@Param("companyTypeUuid")String companyTypeUuid);

}
