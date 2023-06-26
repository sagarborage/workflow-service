package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.CompanyTypeEntity;
import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import com.sowermate.tenantService.entities.GlassTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyTypeEntity ,String> {

   public List<CompanyTypeEntity> findByCompanyTypeId(int companyTypeId);

    public CompanyTypeEntity findByCompanyTypeUuid(@Param("companyTypeUuid")String companyTypeUuid);


}
