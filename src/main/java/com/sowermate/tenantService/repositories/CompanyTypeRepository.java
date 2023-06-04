package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.CompanyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyTypeRepository extends JpaRepository<CompanyTypeEntity ,String> {

    public List<CompanyTypeEntity> findByCompanyTypeId(int companyTypeId);

}
